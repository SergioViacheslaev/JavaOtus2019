package ru.otus.loggingAOP.proxy;

import ru.otus.loggingAOP.proxy.annotatons.Log;

/**
 * @author Sergei Viacheslaev
 */
public interface MyClassInterface {

    void calculation(int param);


    void processTransaction(int accountFrom, double debet, int accountTo);

    void printHello(int param, String param2);

    void printHello(int param);

    void printHello();
}
