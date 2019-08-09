package ru.otus.loggingAOP.proxy;

/**Реализация перехвата вызова методов через паттерн Proxy
 * @author Sergei Viacheslaev
 */
public class Main {
    public static void main(String[] args) {
       MyClassInterface testLogging = ProxyHandler.createMyClass();

       testLogging.printHello();
       testLogging.calculation(10);
       testLogging.processTransaction(236_484_393,2100.500d,874_786_853);




    }
}

