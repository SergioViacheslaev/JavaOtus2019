package ru.otus.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.DbExecutor;
import ru.otus.orm.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.orm.h2.DataSourceH2;
import ru.otus.orm.model.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Sergei Viacheslaev
 */
public class Main {
    private final static String USER_TABLE = "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3));";
    private final static String ACCOUNT_TABLE = "create table account(id bigint(20) NOT NULL auto_increment, type varchar(255), rest NUMBER);";
    private static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        createTable(dataSource, USER_TABLE);
        User user1 = new User("Sergei.V", 32);

        SessionManager sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutor<>(sessionManager);

        dbExecutor.create(user1);

        selectAllRecords(dataSource,"user");


   /*     createTable(dataSource, USER_TABLE);
        User user1 = new User("Sergei.V", 32);
        User user2 = new User(3, "Alex", 15);
        System.out.println(user2);


        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        dbexecutor<User> dbExecutor = new dbexecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        long id = dbServiceUser.saveUser(user1);
        long id2 = dbServiceUser.saveUser(user2);

        Optional<User> user_1 = dbServiceUser.getUser(id);
        Optional<User> user_2 = dbServiceUser.getUser(id2);

        System.out.println(user_1);
        user_1.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );


        System.out.println(user_2);
        user_2.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );


        selectAllRecords(dataSource, "user");

        //Работет с Account
        createTable(dataSource, ACCOUNT_TABLE);
        Account accoun1 = new Account("credit", 123.45d);

        //Сохраняем аккаунт в базу

        sessionManager.beginSession();
        dbexecutor<Account> accountDbExecutor = new dbexecutor<>();
        accountDbExecutor.insertRecord(sessionManager.getConnection(), "insert into account(type,rest) values (?,?)",
                new ArrayList<String>(
                        Arrays.asList(accoun1.getType(), String.valueOf(accoun1.getRest()))));

        sessionManager.commitSession();
        sessionManager.close();

        selectAllRecords(dataSource, "account");
*/

        // Account accountFromDB = dbServiceUser.getUser(1);


    }

    private static void createTable(DataSource dataSource, String sqlCommand) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
            pst.executeUpdate();


        }
        String tableName = sqlCommand.substring(sqlCommand.indexOf("table") + 6, sqlCommand.indexOf("("));
        logger.info("Table '{}' created.", tableName);


    }

    private static void insertRecordInUserTable(DataSource dataSource, String name, int age) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("insert into user(name, age) values (?, ?)")) {
            Savepoint savePoint = connection.setSavepoint("startSavepoint");
            pst.setString(1, name);
            pst.setInt(2, age);
            try {
                int rowCount = pst.executeUpdate(); //Блокирующий вызов
                connection.commit();
                logger.info("inserted rowCount: {}", rowCount);
            } catch (SQLException ex) {
                connection.rollback(savePoint);
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void selectRecordUserTable(DataSource dataSource, long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("select name from user where id  = ?")) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                StringBuilder outString = new StringBuilder();
                outString.append("name: ");
                while (rs.next()) {
                    outString.append(rs.getString("name"));
                }
                logger.info(outString.toString());
            }
        }
    }

    private static void selectAllRecords(DataSource dataSource, String tableName) throws SQLException {
        String selectAllQuery = "select * from |tableNameSupplier|".replace("|tableNameSupplier|", tableName);
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
        /*       while (rs.next()) {


                    System.out.printf("Id: %d%n", rs.getInt(1));
                    System.out.printf("name: %s%n", rs.getString("name"));
                    System.out.printf("age: %d%n", rs.getInt("age"));
                }*/

            }
        }
    }


}
