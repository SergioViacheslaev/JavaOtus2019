package ru.otus.atmDepartment.cassette;


import ru.otus.atmDepartment.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

import java.io.Serializable;
import java.util.*;

/**
 * Хранилище кассет
 *
 * @author Sergei Viacheslaev
 */
public class CassettesStorage implements Cassette, Serializable, Cloneable  {
    private  Map<Integer, AtmCassette> CASSETTE_STORAGE = new TreeMap<>(); // номинал/кассета

    public CassettesStorage() {
        try {
            initializeCassettesStorage(FaceValue.values());
        } catch (CassetteOutOfAmountException e) {
            e.printStackTrace();
        }
    }



    @Override
    public CassettesStorage clone() throws CloneNotSupportedException {
        return (CassettesStorage) super.clone();
    }

    @Override
    public void receiveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException, CassetteOutOfAmountException {
        if (faceValue == null || banknotesAmount <= 0) {
            throw new CassetteOutOfAmountException("Банкомат не принял деньги, проверьте данные !");
        }

        AtmCassette cassette = CASSETTE_STORAGE.get(faceValue.getIntValue());

        if (cassette.isCassetteFull() || cassette.getCassetteFreeSlots() < banknotesAmount) {
            throw new CassetteIsFullException(String.format("Банкомат не принял деньги, кассета для номинала %s - полная!%n", faceValue));
        }

        cassette.setCassetteBanknotesAmount(cassette.getCassetteBanknotesAmount() + banknotesAmount);
    }


    public Map<Integer, AtmCassette> getCassetteStorage() {
        return CASSETTE_STORAGE;
    }

    public int getCassetesStorageBalance() {
        return CASSETTE_STORAGE.values().stream().mapToInt(AtmCassette::getCassetteCashBalance).sum();
    }


    private void initializeCassettesStorage(FaceValue... faceValues) throws CassetteOutOfAmountException {
        for (FaceValue faceValue : faceValues) {
            CASSETTE_STORAGE.put(faceValue.getIntValue(), new AtmCassette(faceValue, 50));
        }

    }

    public void setCASSETTE_STORAGE(Map<Integer, AtmCassette> CASSETTE_STORAGE) {
        this.CASSETTE_STORAGE = CASSETTE_STORAGE;
    }
}
