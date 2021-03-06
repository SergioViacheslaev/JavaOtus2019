package ru.otus.testFramework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.testFramework.annotations.After;
import ru.otus.testFramework.annotations.Before;
import ru.otus.testFramework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tully.
 */
@SuppressWarnings({"SameParameterValue", "BooleanVariableAlwaysNegated"})
public final class ReflectionHelper {
    private static Logger log = LoggerFactory.getLogger(ReflectionHelper.class);

    private ReflectionHelper() {
    }


    //Get instance of specified class
    static <T> T instantiate(Class<T> type) {


        try {
            final Constructor<T> constructor = type.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return null;
    }

    //Get Class object
    static Class<?> getClassObject(String testsClassName) {

        try {
            return Class.forName(testsClassName);
        } catch (ClassNotFoundException e) {
            log.error("Класс {} не найден", testsClassName);
            e.printStackTrace();
            return null;
        }
    }


    //Get all Methods from class
    static Method[] getAllDeclaredMethods(String testsClassName) {

        Class<?> testsClazz = getClassObject(testsClassName);

        //Get all methods from file
        Method[] methods = testsClazz.getDeclaredMethods();


        if (methods.length != 0) {
            return methods;
        } else {
            log.error("В классе {} нет ни одного метода !", testsClazz);
            return new Method[0];
        }


    }


    //Get list of all methods whith specified AnnotationClass from Methods Array
    static List<Method> getAnnotatedMethodsList(Class<? extends Annotation> annotationClassName, Method[] methods) {
        List<Method> annotatedMethodsList = new ArrayList<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClassName)) {
                annotatedMethodsList.add(method);
            }
        }

        if (annotatedMethodsList.isEmpty()) {
            log.error("No annotiated @{} methods found !", annotationClassName);
            return null;
        }

        return annotatedMethodsList;
    }


}
