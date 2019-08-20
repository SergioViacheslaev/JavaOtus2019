package ru.otus.ATM.bankomats;

import ru.otus.ATM.*;
;import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber extends ATM implements AtmStatus {


    public BankomatSber() {
        initializeCassettesStorage(FaceValue.values());

    }

    @Override
    public void receiveCash(FaceValue faceValue, int cashAmount) {
        if (faceValue == null || cashAmount <= 0) {
            System.out.println("Банкомат не принял деньги, проверьте данные !");
            return;
        }
        Cassette cassette = getCassettesStorage().get(faceValue.getIntValue());
        cassette.setBanknotesAmount(cassette.getBanknotesAmount() + cashAmount);

    }

    @Override
    public void printCassetteStorage() {
        getCassettesStorage().values().forEach(System.out::println);
    }

    @Override
    public void showCurrentAtmCashBalance() {
        System.out.printf("Текущий баланс наличных денег в банкомате: %d%n", getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return calculateAtmCashBalance();
    }

    private void initializeCassettesStorage(FaceValue... faceValues) {
        for (FaceValue faceValue : faceValues) {
            getCassettesStorage().put(faceValue.getIntValue(), new Cassette(faceValue, 0));
        }

    }

    private int calculateAtmCashBalance() {
        return getCassettesStorage().values().stream().mapToInt(Cassette::getCassetteCashBalance).sum();
    }


}
