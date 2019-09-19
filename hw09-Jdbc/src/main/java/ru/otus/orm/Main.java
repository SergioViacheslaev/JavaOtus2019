package ru.otus.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.DbExecutor;
import ru.otus.orm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.orm.h2.DataSourceH2;
import ru.otus.orm.model.Account;
import ru.otus.orm.model.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    private final static String TABLE_USER = "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3));";
    private final static String TABLE_ACCOUNT = "create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest Double);";
    private static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        SessionManager sessionManager = new SessionManagerJdbc(dataSource);

        //Table User
        createTable(dataSource, TABLE_USER);
        DbExecutor<User> userDbExecutor = new DbExecutor<>(sessionManager);

        userDbExecutor.create(new User("Tom", 32));
        userDbExecutor.create(new User("Jerry", 16));
        selectAllRecords(dataSource, "user");


        userDbExecutor.update(new User(2, "Snoopy", 99));
        selectAllRecords(dataSource, "user");

        //Table Account
        createTable(dataSource, TABLE_ACCOUNT);
        DbExecutor<Account> accDbExecutor = new DbExecutor<>(sessionManager);

        accDbExecutor.create(new Account("debit", 123.5));
        accDbExecutor.create(new Account("credit", 2000.123));
        accDbExecutor.create(new Account("beneficent", 500_000));
        selectAllRecords(dataSource, "account");

        accDbExecutor.update(new Account(3, "account_closed", 0.0));
        selectAllRecords(dataSource, "account");


    /*    //Загружаем объекты из базы, по ID
        Account accCredit = accDbExecutor.load(1, Account.class);
        System.out.println(accCredit);

        User user2 = userDbExecutor.load(2, User.class);
        System.out.println(user2);
*/

    }

    private static void createTable(DataSource dataSource, String sqlCommand) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
            pst.executeUpdate();
        }
        String tableName = sqlCommand.substring(sqlCommand.indexOf("table") + 6, sqlCommand.indexOf("("));
        logger.info("Table '{}' created.", tableName);

    }

    private static void selectAllRecords(DataSource dataSource, String tableName) throws SQLException {
        String selectAllQuery = String.format("select * from %s", tableName);
        String dataBaseName = dataSource.getConnection().getMetaData().getDatabaseProductName();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(selectAllQuery)) {

            try (ResultSet rs = pst.executeQuery()) {

                System.out.printf("-----Database %s, table '%s'-----%n", dataBaseName, tableName);

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    System.out.printf("%s\t\t", metaData.getColumnName(columnIndex));
                    if (columnIndex == columnCount) System.out.println();
                }
                while (rs.next()) {
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        Object object = rs.getObject(columnIndex);
                        System.out.printf("%s\t\t", object == null ? "NULL" : object.toString());
                    }
                    System.out.printf("%n");
                }

            }
        }
    }


}
