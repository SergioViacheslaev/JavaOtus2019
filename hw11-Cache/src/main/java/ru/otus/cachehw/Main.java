package ru.otus.cachehw;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.api.dao.UserDao;
import ru.otus.cachehw.api.model.AddressDataSet;
import ru.otus.cachehw.api.model.PhoneDataSet;
import ru.otus.cachehw.api.model.User;
import ru.otus.cachehw.api.services.DBServiceCachedUser;
import ru.otus.cachehw.cache.impl.MyCache;
import ru.otus.cachehw.hibernate.HibernateUtils;
import ru.otus.cachehw.hibernate.dao.UserDaoHibernate;
import ru.otus.cachehw.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);

        //Initializing Cache with two sample listeners
        MyCache<String, User> cacheUser = new MyCache<>();
        cacheUser.addListener((key, value, action) -> logger.info("Listener #1, key:{}, value:{}, action: {}", key, value, action));
        cacheUser.addListener((key, value, action) -> logger.info("Listener #2, key:{}, value:{}, action: {}", key, value, action));
        cacheUser.addListener(null);

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

        User cachedUser = userFromCache.get();

        System.out.println("-----------After GC-----------");
        System.gc();

        //User is loaded and also cached
        Optional<User> mayBeCreatedUser = dbServiceCachedUser.getUser(id);
        User loadedFromDBuser = mayBeCreatedUser.get();

        System.out.println("------Will get user from Cache-----");
        Optional<User> optionalUser1 = dbServiceCachedUser.getUser(1L);
        Optional<User> optionalUser2 = dbServiceCachedUser.getUser(33L);


    }

}
