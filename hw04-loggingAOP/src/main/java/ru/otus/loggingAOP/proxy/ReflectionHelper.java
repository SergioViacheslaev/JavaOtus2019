package ru.otus.loggingAOP.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class ReflectionHelper {

    //Получаем множество сигнатур методов помеченных @Log
    public static Set<String> getAnnotatedMethodFullNamesSet(Class<? extends Annotation> annotationClass, Class<?> myClass) {
        Set<String> annotatedMethodsFullNameSet= new HashSet<>();

        Method[] methods = myClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                Parameter[] parameters = method.getParameters();
                annotatedMethodsFullNameSet.add(method.getName() + Arrays.toString(parameters));
            }
        }

        if (annotatedMethodsFullNameSet.isEmpty()) {
            System.out.println("No annotiated @Log methods found !");
            return Collections.emptySet();
        }

        return annotatedMethodsFullNameSet;
    }
}
