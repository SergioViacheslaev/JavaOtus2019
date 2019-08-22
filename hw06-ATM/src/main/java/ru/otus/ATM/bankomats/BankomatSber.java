package ru.otus.ATM.bankomats;

import ru.otus.ATM.*;
import ru.otus.ATM.Cassette.AtmCassette;
import ru.otus.ATM.Cassette.CassetteIsFullException;
import ru.otus.ATM.Cassette.CassetteOutOfAmountException;
import ru.otus.ATM.Cassette.CassettesStorage;
;import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber extends ATM {
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
        return super.giveCash(cashAmount);
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
        return CASSETTES_STORAGE.getCassetteStorage().values().stream().mapToInt(AtmCassette::getCassetteCashBalance).sum();
    }


    /* @Override
    public void printCassetteStorage() {
        getCASSETTE_STORAGE().values().forEach(System.out::println);
    }

    @Override
    public void showCurrentAtmCashBalance() {
        System.out.printf("Текущий баланс наличных денег в банкомате: %d%n", getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return calculateAtmCashBalance();
    }



    private int calculateAtmCashBalance() {
        return getCassettesStorage().values().stream().mapToInt(AtmCassette::getCassetteCashBalance).sum();
    }*/


}
