package ru.otus.orm.reflection;

import ru.otus.orm.annotations.Id;
import ru.otus.orm.jdbc.dbexecutor.SqlCommand;
import ru.otus.orm.jdbc.dbexecutor.exceptions.DbExecutorException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class ReflectionHelper {

    public static List<String> getIdFieldParams(Object object) {
        String idFieldName = null;
        String idValue = null;

        //Ищем поле, аннотированное @Id
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    idFieldName = field.getName();
                    field.setAccessible(true);
                    idValue = field.get(object).toString();
                    field.setAccessible(false);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Objects.isNull(idFieldName)) throw new DbExecutorException("Object has no 'Id' annotation !");

        return Arrays.asList(idFieldName, idValue);
    }

    public static String getIdFieldName(Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    }


    public static Map<String, List<String>> getSqlParams(SqlCommand sqlCommand, Object object) {
        String tableName = object.getClass().getSimpleName();
        String idValue = null;
        String idColumnName = null;
        String sqlString = null;
        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();
        try {

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (!field.isAnnotationPresent(Id.class)) {
                    columnNames.add(field.getName());
                    columnValues.add(field.get(object).toString());
                } else {
                    idColumnName = field.getName();
                    idValue = field.get(object).toString();
                }
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        if(Objects.isNull(idColumnName)) throw new DbExecutorException("Object has no 'Id' annotation !");

        switch (sqlCommand) {
            case INSERT:
                sqlString = String.format("insert into %s(%s,%s) values (?,?)",
                        tableName, columnNames.get(0), columnNames.get(1));
                break;
            case UPDATE:
                sqlString = String.format("update %s set %s = ?, %s = ? where %s=%s ",
                        tableName, columnNames.get(0), columnNames.get(1), idColumnName, idValue);
                break;
        }

        return Collections.singletonMap(sqlString, columnValues);

    }


}
