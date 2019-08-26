package ru.otus.atmDepartment.cassette;


import ru.otus.atmDepartment.cassette.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

import java.io.Serializable;
import java.util.*;

/**
 * Хранилище кассет
 *
 * @author Sergei Viacheslaev
 */
public class CassettesStorage implements Cassette, Serializable {
    private final Map<Integer, AtmCassette> CASSETTE_STORAGE = new TreeMap<>(); // номинал/кассета

    public CassettesStorage() {
        try {
            initializeCassettesStorage(FaceValue.values());
        } catch (CassetteOutOfAmountException e) {
            e.printStackTrace();
        }
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
}
