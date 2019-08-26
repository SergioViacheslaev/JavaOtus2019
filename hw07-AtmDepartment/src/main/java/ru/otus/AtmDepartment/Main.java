package ru.otus.AtmDepartment;



import ru.otus.AtmDepartment.bankomats.ATM;
import ru.otus.AtmDepartment.bankomats.BankomatSber;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {

        AtmDepartment sberAtmDepartment = new AtmDepartment();

        sberAtmDepartment.getAtmList().forEach(System.out::println);



/*
        bankomatSber.receiveCash(FaceValue.ONE_THOUSAND, 100);
        bankomatSber.showCurrentAtmCashBalance();
        bankomatSber.printCassetteStorage();

        Map<FaceValue, Integer> cash = bankomatSber.giveCash(5900);
        cash.forEach((k, v) -> System.out.println(k + " " + v));
        bankomatSber.showCurrentAtmCashBalance();
        bankomatSber.printCassetteStorage();


        bankomatSber.giveCash(2530);
        bankomatSber.showCurrentAtmCashBalance();

        //Проверка переполнения (максимальный лимит банкнот внутри каждой кассеты = 1000)
        bankomatSber.receiveCash(FaceValue.ONE_HUNDRED, 990);*/

    }
}

