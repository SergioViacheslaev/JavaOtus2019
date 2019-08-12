package ru.otus.loggingAOP.proxy;

import ru.otus.loggingAOP.proxy.annotatons.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;

class ProxyHandler {

    static MyClassInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (MyClassInterface) Proxy.newProxyInstance(ProxyHandler.class.getClassLoader(),
                new Class<?>[]{MyClassInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final MyClassInterface myClass;
        private Set<String> annotatedMethodsFullNameSet;

        //В констукторе получаем сразу все множество @Log методов
        DemoInvocationHandler(MyClassInterface myClass) {
            this.myClass = myClass;
            annotatedMethodsFullNameSet = ReflectionHelper.getAnnotatedMethodFullNamesSet(Log.class, myClass.getClass());

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


            //Формируем сигнатуру вызванного метода и проверяем есть ли она в сете
            String invokedMethodFullName = method.getName() + Arrays.toString(method.getParameters());
            if (annotatedMethodsFullNameSet.contains(invokedMethodFullName)) {
                System.out.printf("executed method: %s, param: %s%n", method.getName(), Arrays.toString(args));

            }


            return method.invoke(myClass, args);
        }


    }

}
