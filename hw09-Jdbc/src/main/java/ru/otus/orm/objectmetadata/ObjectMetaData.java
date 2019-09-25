package ru.otus.orm.objectmetadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class ObjectMetaData<T> {
    private String tableName;
    private String idFieldName;
    private String sqlSelect;
    private String sqlInsert;
    private String sqlUpdate;
    private Field idField;
    private List<Field> notIdFields = new ArrayList<>();
    private List<Field> allDeclaredFields = new ArrayList<>();
    private Constructor<T> entityConstructor;

    public String getTableName() {
        return tableName;
    }

    public List<Field> getNotIdFields() {
        return notIdFields;
    }

    public String getSqlInsert() {
        return sqlInsert;
    }

    public Field getIdField() {
        return idField;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public Constructor<T> getEntityConstructor() {
        return entityConstructor;
    }

    public String getSqlUpdate() {
        return sqlUpdate;
    }

    public List<Field> getAllDeclaredFields() {
        return allDeclaredFields;
    }

    void setIdField(Field idField) {
        this.idField = idField;
    }

    void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    String getIdFieldName() {
        return idFieldName;
    }

    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    void setNotIdFields(List<Field> notIdFields) {
        this.notIdFields = notIdFields;
    }

    void setSqlInsert(String sqlInsert) {
        this.sqlInsert = sqlInsert;
    }

    void setSqlSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
    }

    void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    void setEntityConstructor(Constructor<T> entityConstructor) {
        this.entityConstructor = entityConstructor;
    }

    void setAllDeclaredFields(List<Field> allDeclaredFields) {
        this.allDeclaredFields = allDeclaredFields;
    }
}
