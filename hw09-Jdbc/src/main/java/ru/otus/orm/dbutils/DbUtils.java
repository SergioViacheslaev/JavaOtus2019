package ru.otus.orm.dbutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Sergei Viacheslaev
 */
public class DbUtils {

    private static Logger logger = LoggerFactory.getLogger(DbUtils.class);

   public static void createTable(DataSource dataSource, String sqlCommand) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
            pst.executeUpdate();
        }
        String tableName = sqlCommand.substring(sqlCommand.indexOf("table") + 6, sqlCommand.indexOf("("));
        logger.info("Table '{}' created.", tableName);

    }


  public   static void selectAllRecords(DataSource dataSource, String tableName) throws SQLException {
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
