package ru.otus.orm.reflection;

import ru.otus.orm.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Arrays.asList(idFieldName, idValue);
    }
}
