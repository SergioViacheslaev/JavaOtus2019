package ru.otus.cachehw;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.api.dao.UserDao;
import ru.otus.cachehw.api.model.AddressDataSet;
import ru.otus.cachehw.api.model.PhoneDataSet;
import ru.otus.cachehw.api.model.User;
import ru.otus.cachehw.api.service.DBServiceUser;
import ru.otus.cachehw.api.service.DbServiceUserImpl;
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
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        //Create User entity with Address and Phones
        User user = new User("Sergei", 22);

        AddressDataSet address = new AddressDataSet("Galernaya 42");
        user.setAddress(address);

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet("+7 12345", user));
        phones.add(new PhoneDataSet("812 777-1-777", user));
        phones.add(new PhoneDataSet("+7 495 911911235", user));
        user.setPhones(phones);


        //Save user
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        User loadedUser = mayBeCreatedUser.get();

        System.out.println(loadedUser.getPhones());
        System.out.println(loadedUser);


    }

}
