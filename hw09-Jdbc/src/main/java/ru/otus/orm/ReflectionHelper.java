package ru.otus.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class ReflectionHelper {
    public static List<Object> getColumnValues(List<Field> notIdFields, Object objectData) {
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
