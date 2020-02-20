package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByClassName = new HashMap<>();
    private final MethodComparator methodComparator = new MethodComparator();

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponentsByClassName.get(componentClass.getSimpleName());
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
        List<Method> componentMethods = getAppComponentMethods(configClass);
        Object configInstance = createInstance(configClass).orElseThrow(() -> new RuntimeException("Can't create config Instance"));
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

            Object[] initArgs = getInitArgs(methodArgs);
            Object registeredComponent = invokeMethod(appComponentMethod, configInstance, initArgs).
                    orElseThrow(() -> new RuntimeException(String.format("Can't register Component: %s", componentName)));

            appComponentsByName.put(componentName, registeredComponent);
            appComponentsByClassName.put(componentClass.getSimpleName(), registeredComponent);
        }

    }

    private Optional<Object> createInstance(Class<?> clazz) {
        Optional<Object> instance;
        try {
            instance = Optional.of(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Create Instance Exception: ", e);
        }
        return instance;
    }

    private Optional<Object> invokeMethod(Method method, Object object, Object... args) {
        Optional<Object> result;
        try {
            result = Optional.ofNullable(method.invoke(object, args));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Invoke method Exception: ", e);
        }
        return result;
    }


    private Object[] getInitArgs(Class<?>[] componentConstructorArgsTypes) {
        Object[] componentArgs = new Object[componentConstructorArgsTypes.length];
        for (int i = 0; i < componentArgs.length; i++) {
            componentArgs[i] = appComponentsByClassName.get(componentConstructorArgsTypes[i].getSimpleName());
        }
        return componentArgs;
    }

    private List<Method> getAppComponentMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(methodComparator).collect(Collectors.toList());
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
