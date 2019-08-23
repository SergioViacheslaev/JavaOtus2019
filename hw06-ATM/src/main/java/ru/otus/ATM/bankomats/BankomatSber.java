package ru.otus.ATM.bankomats;

import ru.otus.ATM.*;
import ru.otus.ATM.Cassette.exceptions.CassetteIsFullException;
import ru.otus.ATM.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.ATM.Cassette.CassettesStorage;

import java.util.Collections;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber implements ATM {
    private final CassettesStorage CASSETTES_STORAGE = new CassettesStorage();


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
            return CASSETTES_STORAGE.giveBanknotes(cashAmount);
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
        System.out.printf("Текущий баланс наличных денег в банкомате: %d%n", getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return CASSETTES_STORAGE.getCassetesStorageBalance();
    }


}
