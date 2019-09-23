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

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
        this.idFieldName = idField.getName();
    }

    public List<Field> getNotIdFields() {
        return notIdFields;
    }

    public void setNotIdFields(List<Field> notIdFields) {
        this.notIdFields = notIdFields;
    }

    public String getSqlInsert() {
        return sqlInsert;
    }

    public void setSqlInsert(String sqlInsert) {
        this.sqlInsert = sqlInsert;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public void setSqlSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
    }

    public String getSqlUpdate() {
        return sqlUpdate;
    }

    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    public Constructor<T> getEntityConstructor() {
        return entityConstructor;
    }

    public void setEntityConstructor(Constructor<T> entityConstructor) {
        this.entityConstructor = entityConstructor;
    }

    public List<Field> getAllDeclaredFields() {
        return allDeclaredFields;
    }

    public void setAllDeclaredFields(List<Field> allDeclaredFields) {
        this.allDeclaredFields = allDeclaredFields;
    }
}
