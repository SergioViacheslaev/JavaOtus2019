package ru.otus.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.webserver.api.services.UserAuthenticationService;
import ru.otus.webserver.api.web.filters.AuthorizationFilter;
import ru.otus.webserver.servlets.AdminServlet;
import ru.otus.webserver.servlets.LoginServlet;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    private static final int PORT = 8080;
    private static final String STATIC = "/static";
    private static final UserAuthenticationService USER_SERVICE = new UserAuthenticationService();

    public static void main(String[] args) throws Exception {

        new Main().startJettyServer();

    }

    private void startJettyServer() throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(USER_SERVICE)), "/login");
        context.addServlet(new ServletHolder(new AdminServlet()), "/admin");

        //Cover admin page with session-check filter
        context.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin", null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

}
