package ru.otus.orm.jdbc.dbexecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.annotations.Id;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;

import java.lang.reflect.Constructor;
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

    //Object metadata
    private String tableName;
    private String idFieldName;
    private Field idField;
    private List<String> columnNames = new ArrayList<>();
    private List<Field> notIdFields = new ArrayList<>();
    private List<Field> allDeclaredFields = new ArrayList<>();
    private String sqlSelect;
    private String sqlInsert;
    private Constructor<T> entityConstructor;
    private boolean isObjectMetadataSaved = false;


    public DbExecutor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void create(T objectData) {
        if (!isObjectMetadataSaved) saveObjectMetadata(objectData);

        try {
            insertRecord(sqlInsert, getColumnValues(objectData));
        } catch (SQLException e) {
            logger.error("Объект '{}' не сохранен в базе !", tableName);
            e.printStackTrace();
        }

        logger.info("Объект '{}' успешно сохранен в базе !", tableName);
    }

    public void update(T objectData) {
        if (!isObjectMetadataSaved) saveObjectMetadata(objectData);

        int idValue = getObjectIdfromDatabase(objectData);

        String sqlUpdate = String.format("update %s set %s = ?, %s = ? where %s=%d ",
                tableName, columnNames.get(0), columnNames.get(1), idFieldName, idValue);

        try {
            updateRecord(sqlUpdate, getColumnValues(objectData));
        } catch (SQLException e) {
            logger.error("Объект '{}' не обновлен в базе !", objectData.getClass().getSimpleName());
            e.printStackTrace();
        }

        logger.info("Объект '{}' успешно обновлен в базе !", objectData.getClass().getSimpleName());

    }

    private long insertRecord(String sql, List<Object> params) throws SQLException {

        sessionManager.beginSession();
        try (Connection connection = sessionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            sessionManager.commitSession();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            sessionManager.rollbackSession();
            logger.error(ex.getMessage(), ex);
            throw ex;
        }


    }

    public T load(long id, Class<T> clazz) {
        //Проверяем и сохраняем конструктор класса только один раз
        if (Objects.isNull(entityConstructor)) {
            try {
                entityConstructor = clazz.getDeclaredConstructor();
                if (!isObjectMetadataSaved) saveObjectMetadata(entityConstructor.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sessionManager.beginSession();
        Optional<T> optionalInstance = Optional.empty();
        try {
            T instance = entityConstructor.newInstance();
            optionalInstance = selectRecord(sessionManager.getConnection(), sqlSelect, id, resultSet -> {
                try {
                    for (Field field : allDeclaredFields) {
                        field.setAccessible(true);
                        field.set(instance, resultSet.getObject(field.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return instance;

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionManager.close();
        }

        return optionalInstance.orElseThrow(() -> new DbExecutorException("Object is not loaded !"));

    }

    private Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(rsHandler.apply(rs));
                } else {
                    throw new DbExecutorException("No object found with such ID in database !");
                }

            }
        }
    }

    private void updateRecord(String sql, List<Object> params) throws SQLException {
        sessionManager.beginSession();
        try (Connection connection = sessionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            sessionManager.commitSession();

        } catch (SQLException ex) {
            sessionManager.rollbackSession();
            logger.error(ex.getMessage(), ex);
            throw ex;
        }

    }


    private int getObjectIdfromDatabase(T object) {
        String idValue = null;
        try {
            idValue = String.valueOf(idField.get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String selectSQL = String.format("SELECT %s FROM %s WHERE %s = %s", idFieldName, tableName, idFieldName, idValue);

        sessionManager.beginSession();
        //Connection закрывается в try resources
        try (Connection connection = sessionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(selectSQL);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                logger.info("Object '{}' with id={}  found in DB", tableName, idValue);
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("Object '{}' with id={} not found in DB !", tableName, idValue);
        throw new DbExecutorException("No object found with such ID in database !");

    }

    //Запускается только один раз, затем используем уже сохраненные данные
    private void saveObjectMetadata(T object) {
        this.tableName = object.getClass().getSimpleName();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            allDeclaredFields.add(field);
            if (field.isAnnotationPresent(Id.class)) {
                this.idFieldName = field.getName();
                this.idField = field;
            } else {
                this.notIdFields.add(field);
                this.columnNames.add(field.getName());
            }
        }

        this.sqlSelect = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idFieldName);
        this.sqlInsert = String.format("insert into %s(%s,%s) values (?,?)", tableName, columnNames.get(0), columnNames.get(1));

        if (Objects.isNull(idFieldName)) throw new DbExecutorException("Object has no 'Id' annotation !");

        this.isObjectMetadataSaved = true;
        logger.info("'{}' object metadata is saved.", tableName);
    }

    private List<Object> getColumnValues(T objectData) {
        List<Object> columnValues = new ArrayList<>();
        try {
            for (Field field : notIdFields) {
                columnValues.add(field.get(objectData));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return columnValues;
    }


}
