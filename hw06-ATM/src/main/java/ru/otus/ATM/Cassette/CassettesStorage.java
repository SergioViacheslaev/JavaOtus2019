package ru.otus.ATM.Cassette;

import ru.otus.ATM.Cassette.exceptions.CassetteIsFullException;
import ru.otus.ATM.Cassette.exceptions.CassetteOutOfAmountException;
import ru.otus.ATM.FaceValue;

import java.util.*;

/**
 * Хранилище кассет
 *
 * @author Sergei Viacheslaev
 */
public class CassettesStorage implements Cassette {
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

    @Override
    public Map<FaceValue, Integer> giveBanknotes(int cashAmount) throws CassetteOutOfAmountException {
        if (cashAmount > getCassetesStorageBalance()) {
            throw new CassetteOutOfAmountException("В банкомате недостаточно средств для выдачи !");
        } else if (cashAmount <= 0) {
            System.out.println("Проверьте введенную сумму !");
            return Collections.emptyMap();

        }
        return buildCashMap(cashAmount);
    }

    public Map<Integer, AtmCassette> getCassetteStorage() {
        return CASSETTE_STORAGE;
    }

    public int getCassetesStorageBalance() {
        return CASSETTE_STORAGE.values().stream().mapToInt(AtmCassette::getCassetteCashBalance).sum();
    }

    private Map<FaceValue, Integer> buildCashMap(int cashAmount) {
        Map<Integer, AtmCassette> sortedCassettes = new TreeMap<>(Comparator.reverseOrder());
        Map<FaceValue, Integer> cashMap = new HashMap<>();
        sortedCassettes.putAll(CASSETTE_STORAGE);
        int sum = cashAmount;

        for (AtmCassette cassette : sortedCassettes.values()) {
            FaceValue faceValue = cassette.getCASSETTE_FACEVALUE();
            cassette.saveBanknotesAmount();

            while (sum > 0 && faceValue.getIntValue() <= sum && cassette.hasBanknotes()) {
                Integer num = cashMap.getOrDefault(faceValue, 0);
                cashMap.put(faceValue, num + 1);
                sum -= faceValue.getIntValue();
                cassette.decrementBanknotesAmount();
            }
            if (sum == 0) break;
        }

        if (sum > 0) {
            sortedCassettes.values().forEach(AtmCassette::restoreBaknotesAmount);
            System.out.printf("Сумма %d не может быть выдана !%n", cashAmount);
            return Collections.emptyMap();
        }

        System.out.printf("Успешно выдано %d денежных средств, купюрами: %n", cashAmount);
        cashMap.forEach((faceValue, amount) -> System.out.printf("Номинал %s Количество %d%n", faceValue, amount));

        return cashMap;
    }

    private void initializeCassettesStorage(FaceValue... faceValues) throws CassetteOutOfAmountException {
        for (FaceValue faceValue : faceValues) {
            CASSETTE_STORAGE.put(faceValue.getIntValue(), new AtmCassette(faceValue, 50));
        }

    }
}
