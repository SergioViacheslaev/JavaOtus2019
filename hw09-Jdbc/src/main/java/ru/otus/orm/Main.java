package ru.otus.orm;


import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.DbExecutor;
import ru.otus.orm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.orm.h2.DataSourceH2;
import ru.otus.orm.model.Account;
import ru.otus.orm.model.User;
import ru.otus.orm.objectmetadata.ObjectMetaDataHolder;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    private final static String TABLE_USER = "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3));";
    private final static String TABLE_ACCOUNT = "create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest Double);";


    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        SessionManager sessionManager = new SessionManagerJdbc(dataSource);

        //Table User
        DbUtils.createTable(dataSource, TABLE_USER);
        DbExecutor<User> userDbExecutor = new DbExecutor<>(sessionManager, new ObjectMetaDataHolder<>(User.class));
        userDbExecutor.create(new User("Tom", 32));
        userDbExecutor.create(new User("Jerry", 16));
        DbUtils.selectAllRecords(dataSource, "user");

        userDbExecutor.update(new User(2, "Snoopy", 99));
        DbUtils.selectAllRecords(dataSource, "user");


        //Table Account
        DbUtils.createTable(dataSource, TABLE_ACCOUNT);
        DbExecutor<Account> accDbExecutor = new DbExecutor<>(sessionManager, new ObjectMetaDataHolder<>(Account.class));

        accDbExecutor.create(new Account("debit", 123.5));
        accDbExecutor.create(new Account("credit", 2000.123));
        accDbExecutor.create(new Account("beneficent", 500_000));
        DbUtils.selectAllRecords(dataSource, "account");

        accDbExecutor.update(new Account(3, "account_closed", 0.0));
        DbUtils.selectAllRecords(dataSource, "account");


        //Загружаем объекты из базы, по ID
        Account accCredit = accDbExecutor.load(3, Account.class);
        System.out.println(accCredit);

        User user2 = userDbExecutor.load(2, User.class);
        System.out.println(user2);

    }


}
