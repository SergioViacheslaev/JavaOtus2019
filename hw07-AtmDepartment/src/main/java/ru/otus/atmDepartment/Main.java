package ru.otus.atmDepartment;


import ru.otus.atmDepartment.bankomats.ATM;
import ru.otus.atmDepartment.bankomats.BankomatSber;
import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.withdrawStrategies.MinimumBanknotesWithdrawStrategy;

/**
 * @author Sergei Viacheslaev
 */
public class Main {

    public static void main(String[] args) {



        AtmDepartment sberAtmDepartment = new AtmDepartment();
        sberAtmDepartment.getAtmList().forEach(System.out::println);
        sberAtmDepartment.showAtmsTotalCashAmount();



        ATM atm1 = sberAtmDepartment.getAtm("sberatm#1");
        atm1.receiveCash(FaceValue.FIVE_THOUSAND,100);
        sberAtmDepartment.showAtmsTotalCashAmount();

        //Добавим еще банкомат
        sberAtmDepartment.addAtm(new BankomatSber("VipAtm"));
        ATM atmVip = sberAtmDepartment.getAtm("vipAtm");
        atmVip.giveCash(470_000);

        sberAtmDepartment.showAtmsTotalCashAmount();

        //Вернули все банкоматы к начальному состоянию
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();


        atmVip.receiveCash(FaceValue.ONE_THOUSAND,255);
        //Установим новое начальное состояние для банкомата
        atmVip.setAtmStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();

        atm1.giveCash(155_000);
        sberAtmDepartment.showAtmsTotalCashAmount();

        //Вернули все банкоматы к начальному состоянию
        sberAtmDepartment.restoreAllAtmsToStartState();
        sberAtmDepartment.showAtmsTotalCashAmount();







    }
}

