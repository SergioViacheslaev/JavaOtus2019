package ru.otus.atmDepartment;


import ru.otus.atmDepartment.bankomats.ATM;

import java.sql.SQLOutput;
import java.util.Optional;


/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {


        AtmDepartment sberAtmDepartment = new AtmDepartment();
        sberAtmDepartment.getAtmList().forEach(System.out::println);
        sberAtmDepartment.showAtmsTotalCashAmount();


        Optional<ATM> optionalAtm1 = sberAtmDepartment.getAtm("sberatm#1");
        optionalAtm1.ifPresentOrElse(atm -> atm.giveCash(155_000), () -> System.out.println("Нет такого банкомата !"));


        ATM atm1 =  optionalAtm1.orElseThrow(()->new RuntimeException("Такого банкомата не существует !"));



        sberAtmDepartment.showAtmsTotalCashAmount();
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();

        atm1.receiveCash(FaceValue.ONE_THOUSAND, 77);
        sberAtmDepartment.showAtmsTotalCashAmount();

        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();

        Optional<ATM> optionalAtm3 = sberAtmDepartment.getAtm("sberatm#3");

        optionalAtm3.ifPresentOrElse(atm -> atm.giveCash(240_000), () -> System.out.println("Нет такого банкомата !"));

        sberAtmDepartment.showAtmsTotalCashAmount();
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();


    }
}

