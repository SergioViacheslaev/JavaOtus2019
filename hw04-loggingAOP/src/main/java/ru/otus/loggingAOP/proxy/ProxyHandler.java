package ru.otus.loggingAOP.proxy;

import ru.otus.loggingAOP.proxy.annotatons.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ProxyHandler {

    static MyClassInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (MyClassInterface) Proxy.newProxyInstance(ProxyHandler.class.getClassLoader(),
                new Class<?>[]{MyClassInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final MyClassInterface myClass;
        private List<Method> loggedMethods;

        //В констукторе получаем сразу весь список @Log методов
        DemoInvocationHandler(MyClassInterface myClass) {
            this.myClass = myClass;
            loggedMethods = ReflectionHelper.getAnnotatedMethodsList(Log.class, myClass.getClass());

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            /**Проверяем есть ли в списке логированных методов, такой method
             * Через contains(method) - не находит !
             * Сделал через стримы, сравнение по имени
             */
            boolean isLoggedMethod = loggedMethods.stream().anyMatch(loggedMethod -> loggedMethod.getName().equals(method.getName()));

            //Логируем все методы помеченные @Log
            if (isLoggedMethod) {

                System.out.printf("executed method: %s, param: %s%n", method.getName(), Arrays.toString(args));

            }


            return method.invoke(myClass, args);
        }


    }

}
