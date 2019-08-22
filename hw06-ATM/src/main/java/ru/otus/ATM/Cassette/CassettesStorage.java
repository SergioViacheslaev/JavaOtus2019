package ru.otus.ATM.Cassette;

import ru.otus.ATM.FaceValue;

import java.util.Map;
import java.util.TreeMap;

/**
 * Хранилище кассет
 *
 * @author Sergei Viacheslaev
 */
public class CassettesStorage implements Cassette {
    private final Map<Integer, AtmCassette> CASSETTE_STORAGE = new TreeMap<>(); // номинал/кассета

    public CassettesStorage() {
        initializeCassettesStorage(FaceValue.values());
    }

    private void initializeCassettesStorage(FaceValue... faceValues) {
        for (FaceValue faceValue : faceValues) {
            CASSETTE_STORAGE.put(faceValue.getIntValue(), new AtmCassette(faceValue, 50));
        }

    }

    public Map<Integer, AtmCassette> getCassetteStorage() {
        return CASSETTE_STORAGE;
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

    @Override
    public void giveBanknotes(FaceValue faceValue, int banknotesAmount) throws CassetteIsFullException, CassetteOutOfAmountException {

    }
}
