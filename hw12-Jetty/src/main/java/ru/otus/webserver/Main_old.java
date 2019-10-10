package ru.otus.webserver;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.webserver.api.dao.UserDao;
import ru.otus.webserver.api.model.AddressDataSet;
import ru.otus.webserver.api.model.PhoneDataSet;
import ru.otus.webserver.api.model.User;
import ru.otus.webserver.api.services.DBServiceCachedUser;
import ru.otus.webserver.cache.impl.MyCache;
import ru.otus.webserver.hibernate.HibernateUtils;
import ru.otus.webserver.hibernate.dao.UserDaoHibernate;
import ru.otus.webserver.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main_old {
    private final static Logger logger = LoggerFactory.getLogger(Main_old.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);

        //Initializing Cache
        MyCache<String, User> cacheUser = new MyCache<>();

        DBServiceCachedUser dbServiceCachedUser = new DBServiceCachedUser(userDao, cacheUser);
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
        long id = dbServiceCachedUser.saveUser(user);

        Optional<User> userFromCache = dbServiceCachedUser.getUser(id);

        System.out.println("-----------After GC-----------");
        System.gc();

        //User is loaded and also cached
        Optional<User> mayBeCreatedUser = dbServiceCachedUser.getUser(id);

        System.out.println("------Will get user from Cache-----");
        Optional<User> optionalUser1 = dbServiceCachedUser.getUser(1L);
        Optional<User> optionalUser2 = dbServiceCachedUser.getUser(33L);


    }

}
