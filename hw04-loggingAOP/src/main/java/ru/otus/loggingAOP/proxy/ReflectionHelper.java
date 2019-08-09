package ru.otus.loggingAOP.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class ReflectionHelper {

    //Get list of all methods whith specified AnnotationClass from MyClassInterface object
    public static List<Method> getAnnotatedMethodsList(Class<? extends Annotation> annotationClass, Class<?> myClass) {
        List<Method> annotatedMethodsList = new ArrayList<>();

        Method[] methods = myClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotatedMethodsList.add(method);
            }
        }

        if (annotatedMethodsList.isEmpty()) {
            System.out.println("No annotiated @Log methods found !");
            return null;
        }

        return annotatedMethodsList;
    }
}
