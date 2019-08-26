package ru.otus.AtmDepartment.bankomats;


import ru.otus.AtmDepartment.Cassette.CassettesStorage;
import ru.otus.AtmDepartment.Cassette.exceptions.CassetteIsFullException;
import ru.otus.AtmDepartment.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.AtmDepartment.FaceValue;

import java.util.Collections;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber implements ATM {
    private final CassettesStorage CASSETTES_STORAGE = new CassettesStorage();
    private String atmID;
    private WithdrawStrategy withdrawStrategy;


    public BankomatSber(String atmID, WithdrawStrategy withdrawStrategy) {
        this.atmID = atmID;
        this.withdrawStrategy = withdrawStrategy;
    }

    @Override
    public String getAtmID() {
        return atmID;
    }

    @Override
    public void setWithdrawStrategy(WithdrawStrategy withdrawStrategy) {
        this.withdrawStrategy = withdrawStrategy;
    }

    @Override
    public CassettesStorage getCassettesStorage() {
        return CASSETTES_STORAGE;
    }

    @Override
    public void receiveCash(FaceValue faceValue, int banknotesAmount) {
        try {
            CASSETTES_STORAGE.receiveBanknotes(faceValue, banknotesAmount);
            System.out.printf("Банкомат успешно принял сумму %d%n", banknotesAmount * faceValue.getIntValue());
        } catch (CassetteIsFullException | CassetteOutOfAmountException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<FaceValue, Integer> giveCash(int cashAmount) {


        try {
            return withdrawStrategy.withdrawCash(CASSETTES_STORAGE, cashAmount);
        } catch (CassetteOutOfAmountException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

     }

    @Override
    public void printCassetteStorage() {
        CASSETTES_STORAGE.getCassetteStorage().values().forEach(System.out::println);
    }

    @Override
    public void showCurrentAtmCashBalance() {
        System.out.printf("Текущий баланс наличных денег в банкомате %s: %d%n", atmID, getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return CASSETTES_STORAGE.getCassetesStorageBalance();
    }

    @Override
    public String toString() {
        return atmID;
    }


}
