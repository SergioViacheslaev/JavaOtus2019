package ru.otus.loggingAOP.proxy;

import ru.otus.loggingAOP.proxy.annotatons.Log;

import java.util.Random;

/**
 * @author Sergei Viacheslaev
 * Проверочный класс
 */

public class TestLogging implements MyClassInterface {


    @Log
    public void calculation(int param) {
        System.out.printf("Binary presentation of number '%d' is %s%n", param, Integer.toBinaryString(param));

    }


    @Log
    public void processTransaction(int accountFrom, double debet, int accountTo) {
        int transactionNumber = new Random().nextInt(10000);
        System.out.printf("Транзакция #%d успешно завершена !", transactionNumber);

    }


    //@Log
    public void printHello() {
        System.out.println("Hello World !");
    }


}

