package ru.otus.atmDepartment;


import ru.otus.atmDepartment.bankomats.ATM;


/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {


        AtmDepartment sberAtmDepartment = new AtmDepartment();
        sberAtmDepartment.getAtmList().forEach(System.out::println);
        sberAtmDepartment.showAtmsTotalCashAmount();


        ATM atm1 = sberAtmDepartment.getAtm("sberatm#1");

        atm1.giveCash(300_000);

        sberAtmDepartment.showAtmsTotalCashAmount();
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();

        atm1.receiveCash(FaceValue.ONE_THOUSAND, 77);
        sberAtmDepartment.showAtmsTotalCashAmount();

        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();

        ATM atm3 = sberAtmDepartment.getAtm("sberatm#3");
        atm3.giveCash(245_000);

        sberAtmDepartment.showAtmsTotalCashAmount();
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();


    }
}

