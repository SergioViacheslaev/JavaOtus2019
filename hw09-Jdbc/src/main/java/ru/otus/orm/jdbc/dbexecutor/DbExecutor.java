package ru.otus.orm.jdbc.dbexecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.annotations.Id;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;
import ru.otus.orm.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

/**
 * Modified by Sergei.V on 14.09.2019
 *
 * @author Sergey Petrelevich
 * created on 03.02.19.
 */
public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);
    private SessionManager sessionManager;


    public DbExecutor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void create(T objectData) {
        if (!objectHasIdAnnotation(objectData)) throw new DbExecutorException("Object has no 'Id' annotation !");
        sessionManager.beginSession();

        Map<String, List<String>> sqlParams = ReflectionHelper.getSqlParams(SqlCommand.INSERT, objectData);
        String sqlInsertRecord = sqlParams.keySet().iterator().next();
        List<String> columnValues = sqlParams.get(sqlInsertRecord);

        try {
            insertRecord(sessionManager.getConnection(), sqlInsertRecord, columnValues);
            sessionManager.commitSession();
        } catch (SQLException e) {
            logger.error("Объект '{}' не сохранен в базе !", objectData.getClass().getSimpleName());
            e.printStackTrace();
        } finally {
            sessionManager.close();
        }

        logger.info("Объект '{}' успешно сохранен в базе !", objectData.getClass().getSimpleName());
    }

    public void update(T objectData) {
        if (!objectHasIdAnnotation(objectData)) throw new DbExecutorException("Object has no 'Id' annotation !");
        if (!dbHasObject(objectData)) throw new DbExecutorException("No object with such ID in database !");

        sessionManager.beginSession();

        Map<String, List<String>> sqlParams = ReflectionHelper.getSqlParams(SqlCommand.UPDATE, objectData);
        String sqlUpdateString = sqlParams.keySet().iterator().next();
        List<String> columnValues = sqlParams.get(sqlUpdateString);

        try {
            updateRecord(sessionManager.getConnection(), sqlUpdateString, columnValues);
            sessionManager.commitSession();
        } catch (SQLException e) {
            logger.error("Объект '{}' не обновлен в базе !", objectData.getClass().getSimpleName());
            e.printStackTrace();
        } finally {
            sessionManager.close();
        }

        logger.info("Объект '{}' успешно обновлен в базе !", objectData.getClass().getSimpleName());

    }

    public T load(long id, Class<T> clazz) {
        if (!dbHasId(id, clazz)) throw new DbExecutorException("No such Object found in database !");

        String tableName = clazz.getSimpleName();
        String idName = ReflectionHelper.getIdFieldName(clazz);
        String selectSQL = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idName);

        sessionManager.beginSession();
        Optional<T> optionalInstance = Optional.empty();
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            optionalInstance = selectRecord(sessionManager.getConnection(), selectSQL, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        for (Field field : clazz.getDeclaredFields()) {

                            field.setAccessible(true);
                            field.set(instance, resultSet.getObject(field.getName()));
                            field.setAccessible(false);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                return instance;

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return optionalInstance.orElseThrow(() -> new DbExecutorException("Object is not loaded !"));

    }

    private long insertRecord(Connection connection, String sql, List<String> params) throws SQLException {

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

    private void updateRecord(Connection connection, String sql, List<String> params) throws SQLException {

        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();

        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }

    }


    private boolean dbHasObject(T object) {

        String tableName = object.getClass().getSimpleName();
        List<String> idFieldParams = ReflectionHelper.getIdFieldParams(object);
        String idFieldName = idFieldParams.get(0);
        String idValue = idFieldParams.get(1);


        String selectSQL = String.format("SELECT %s FROM %s WHERE %s = %s", idFieldName, tableName, idFieldName, idValue);

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(selectSQL);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                logger.info("Object '{}' with id={}  found in DB", tableName, idValue);
                return true;
            } else {
                logger.info("Object '{}' with id={} not found in DB !", tableName, idValue);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private boolean objectHasIdAnnotation(T object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) return true;
        }

        return false;
    }

    private boolean dbHasId(long id, Class clazz) {
        String tableName = clazz.getSimpleName();
        String idName = ReflectionHelper.getIdFieldName(clazz);

        String selectSQL = String.format("SELECT %s FROM %s WHERE %s = %s", idName, tableName, idName, id);

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(selectSQL);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                logger.info("Object '{}' with id={}  found in DB", tableName, id);
                return true;
            } else {
                logger.info("Object '{}' with id={} not found in DB !", tableName, id);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
