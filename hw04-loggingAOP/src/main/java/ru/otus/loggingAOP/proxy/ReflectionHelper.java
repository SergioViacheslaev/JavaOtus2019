package ru.otus.loggingAOP.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class ReflectionHelper {

    //Получаем список сигнатур методов помеченных @Log
    public static List<String> getAnnotatedMethodFullNamesList(Class<? extends Annotation> annotationClass, Class<?> myClass) {
        List<String> annotatedMethodsFullNameList = new ArrayList<>();

        Method[] methods = myClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                Parameter[] parameters = method.getParameters();
                annotatedMethodsFullNameList.add(method.getName() + Arrays.toString(parameters));
            }
        }

        if (annotatedMethodsFullNameList.isEmpty()) {
            System.out.println("No annotiated @Log methods found !");
            return null;
        }

        return annotatedMethodsFullNameList;
    }
}
