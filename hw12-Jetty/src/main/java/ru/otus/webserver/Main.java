package ru.otus.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.SessionFactory;
import ru.otus.webserver.api.dao.UserDao;
import ru.otus.webserver.api.model.AddressDataSet;
import ru.otus.webserver.api.model.PhoneDataSet;
import ru.otus.webserver.api.model.User;
import ru.otus.webserver.api.services.DBServiceCachedUser;
import ru.otus.webserver.api.services.UserAuthenticationService;
import ru.otus.webserver.api.web.filters.AuthorizationFilter;
import ru.otus.webserver.cache.impl.MyCache;
import ru.otus.webserver.hibernate.HibernateUtils;
import ru.otus.webserver.hibernate.dao.UserDaoHibernate;
import ru.otus.webserver.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.webserver.servlets.AdminServlet;
import ru.otus.webserver.servlets.LoginServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    private static final int PORT = 8080;
    private static final String STATIC = "/static";
    private static final UserAuthenticationService USER_AUTHENTICATION_SERVICE = new UserAuthenticationService();
    private DBServiceCachedUser dbServiceCachedUser;


    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.startHibernate();
        main.startJettyServer();

    }

    private void startJettyServer() throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(USER_AUTHENTICATION_SERVICE)), "/login");
        context.addServlet(new ServletHolder(new AdminServlet(dbServiceCachedUser)), "/admin");

        //Cover admin page with session-check filter
        context.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin", null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();


    }

    private void startHibernate() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);

        //Initializing Cache
        MyCache<String, User> cacheUser = new MyCache<>();

        dbServiceCachedUser = new DBServiceCachedUser(userDao, cacheUser);


        //Creating User
        User user = new User("Sergei", 22);
        AddressDataSet address = new AddressDataSet("Galernaya 42");
        user.setAddress(address);

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("+7 12345", user));
        phones.add(new PhoneDataSet("812 777-1-777", user));
        phones.add(new PhoneDataSet("+7 495 911911235", user));
        user.setPhones(phones);

        //Save user
        dbServiceCachedUser.saveUser(user);


    }

}
