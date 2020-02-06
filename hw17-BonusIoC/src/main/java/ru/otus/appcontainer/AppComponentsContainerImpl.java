package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, String> componentNameByClassName = new HashMap<>();
    private final MethodComparator methodComparator = new MethodComparator();

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        String componentName = componentNameByClassName.get(componentClass.getSimpleName());
        return (C) appComponentsByName.get(componentName);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        List<Method> componentMethods = parseConfigClass(configClass);
        Object configInstance = createInstance(configClass).orElseThrow();
        registerComponents(configInstance, componentMethods);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void registerComponents(Object configInstance, List<Method> componentMethods) {

        for (Method appComponentMethod : componentMethods) {
            AppComponent annotation = appComponentMethod.getAnnotation(AppComponent.class);
            Class<?> componentClass = appComponentMethod.getReturnType();
            Class<?>[] methodArgs = appComponentMethod.getParameterTypes();
            String componentName = annotation.name();
            componentNameByClassName.put(componentClass.getSimpleName(), componentName);

            Object registeredComponent;
            if (methodArgs.length == 0) {
                registeredComponent = invokeMethod(appComponentMethod, configInstance).orElseThrow();
            } else {
                Object[] initArgs = getInitArgs(methodArgs);
                registeredComponent = invokeMethod(appComponentMethod, configInstance, initArgs).orElseThrow();
            }
            appComponentsByName.put(componentName, registeredComponent);
        }

    }

    private List<Method> parseConfigClass(Class<?> configClass) {
        Set<Method> appComponentMethods = getAppComponentMethods(configClass);
        List<Method> sortedComponentMethods = new ArrayList<>(appComponentMethods);
        sortedComponentMethods.sort(methodComparator);
        return sortedComponentMethods;
    }

    private Optional<Object> createInstance(Class<?> clazz) {
        Optional<Object> instance = Optional.empty();
        try {
            instance = Optional.of(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private Optional<Object> invokeMethod(Method method, Object object, Object... args) {
        Optional<Object> result = Optional.empty();
        try {
            result = Optional.ofNullable(method.invoke(object, args));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    private Object[] getInitArgs(Class<?>[] componentConstructorArgsTypes) {
        List<Object> componentArgs = new ArrayList<>();
        for (Class<?> argType : componentConstructorArgsTypes) {
            Object argObject = appComponentsByName.get(componentNameByClassName.get(argType.getSimpleName()));
            componentArgs.add(argObject);
        }
        return componentArgs.toArray();
    }

    private Set<Method> getAppComponentMethods(Class<?> configClass) {
        Reflections reflections = new Reflections(
                configClass,
                new MethodAnnotationsScanner());
        return reflections.getMethodsAnnotatedWith(AppComponent.class);
    }

    private static class MethodComparator implements Comparator<Method> {
        @Override
        public int compare(Method method1, Method method2) {
            int orderMethod1 = method1.getAnnotation(AppComponent.class).order();
            int orderMethod2 = method2.getAnnotation(AppComponent.class).order();

            return Integer.compare(orderMethod1, orderMethod2);
        }
    }

}
