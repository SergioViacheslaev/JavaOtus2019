package ru.otus.atmDepartment.withdrawStrategies;

import ru.otus.atmDepartment.cassette.AtmCassette;
import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

import java.io.Serializable;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class MinimumBanknotesWithdrawStrategy implements WithdrawStrategy, Serializable {
    private CassettesStorage cassettesStorage;

    @Override
    public WithdrawStrategy clone() throws CloneNotSupportedException {
        return (WithdrawStrategy) super.clone();
    }

    @Override
    public Map<FaceValue, Integer> withdrawCash(CassettesStorage cassettesStorage, int cashAmount) throws CassetteOutOfAmountException {
        if (cashAmount > cassettesStorage.getCassetesStorageBalance()) {
            throw new CassetteOutOfAmountException("В банкомате недостаточно средств для выдачи !");
        } else if (cashAmount <= 0) {
            System.out.println("Проверьте введенную сумму !");
            return Collections.emptyMap();

        }

        this.cassettesStorage = cassettesStorage;
        return buildCashMap(cashAmount);
    }

    private Map<FaceValue, Integer> buildCashMap(int cashAmount) {
        Map<Integer, AtmCassette> sortedCassettes = new TreeMap<>(Comparator.reverseOrder());
        Map<FaceValue, Integer> cashMap = new HashMap<>();
        sortedCassettes.putAll(cassettesStorage.getCassetteStorage());
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
}
