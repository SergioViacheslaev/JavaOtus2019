package ru.otus.orm.jdbc.dbexecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.annotations.Id;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);
    private SessionManager sessionManager;


    public DbExecutor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void create(T object) {
        if (!objectHasIdAnnotation(object)) throw new DbExecutorException("Object has no 'Id' annotation !");


        sessionManager.beginSession();
        //todo:сформировать sql запрос
        String sqlInsertRecord = "insert into user(name,age) values (?,?)";
        try {
            insertRecord(sessionManager.getConnection(), sqlInsertRecord, new ArrayList<>(
                    Arrays.asList("Alex", String.valueOf(32))));

            sessionManager.commitSession();
        } catch (SQLException e) {
            System.out.println("Не удалось сохранить объект в базу");
            e.printStackTrace();

        } finally {
            sessionManager.close();
        }

        System.out.println("Объект такой то успешно сохранен в базу !");


    }

    public long insertRecord(Connection connection, String sql, List<String> params) throws SQLException {

        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

    //Check object has @Id annotation
    private boolean objectHasIdAnnotation(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) return true;
        }

        return false;
    }
}
