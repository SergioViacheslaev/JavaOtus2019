package ru.otus.ATM.WithdrawStrategies;

import ru.otus.ATM.Cassette;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.bankomats.ATM;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sergei Viacheslaev
 */
public class largeCashWithdawStrategy implements CashWithdrawStrategy {
    private ATM atm;
    private Map<Integer, Cassette> sortedCassettes;

    public largeCashWithdawStrategy(ATM atm) {
        this.atm = atm;
        sortedCassettes = new TreeMap<>(Comparator.reverseOrder());
        sortedCassettes.putAll(atm.getCassettesStorage());

    /*    sortedCassettes = atm.getCassettesStorage().entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));*/


    }

    @Override
    public Map<FaceValue, Integer> withdrawCash(int cashAmount) {
        if (cashAmount > atm.getAtmCashBalance()) {
            System.out.println("В банкомате недостаточно средств для выдачи !");
            return Collections.emptyMap();
        } else if (cashAmount <= 0) {
            System.out.println("Проверьте введенную сумму !");
            return Collections.emptyMap();

        }


        return buildCashMap(cashAmount);
    }


    private Map<FaceValue, Integer> buildCashMap(int cashAmount) {
        Map<FaceValue, Integer> cashMap = new HashMap<>();

        int sum = cashAmount;

        for (Cassette cassette : sortedCassettes.values()) {
            FaceValue faceValue = cassette.getCassetteFacevalue();
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
            sortedCassettes.values().forEach(Cassette::restoreBaknotesAmount);
            System.out.printf("Сумма %d не может быть выдана !%n", cashAmount);
            return Collections.emptyMap();
        }

        System.out.printf("Успешно выдано %d денежных средств, купюрами: %n", cashAmount);
        cashMap.forEach((faceValue, amount) -> System.out.printf("Номинал %s Количество %d%n", faceValue, amount));

        return cashMap;

    }





    /*    @Override
    public Cash withdrawCash(int withdrawAmount) {
        if (withdrawAmount >  ) {
            System.out.println("В банкомате недостаточно средств !");
            return;
        }

        Map<FaceValue, Integer> withdrawnBanknotes = new TreeMap<>();

        int sum = withdrawAmount;


        for (BankomatSber.Cassette cassette : cassetesStorage.values()) {
            //Временно сохраняем изначальное кол-во банкнот
            cassette.savebanknotesAmount();

            FaceValue faceValue = cassette.getCassetteFaceValue();

            while (sum > 0 && faceValue.getIntValue() <= sum && cassette.hasBanknotes()) {
                Integer num = withdrawnBanknotes.getOrDefault(faceValue, 0);
                withdrawnBanknotes.put(faceValue, num + 1);
                sum -= faceValue.getIntValue();
                cassette.decrementAmount();
            }
            if (sum == 0) break;

        }


        //В случае невозможности выдачи
        if (sum > 0) {
            //Восстановливаем состояние кол-ва банкнот
            restorebanknotesAmount();
            System.out.printf("Сумма %d не может быть выдана !%n", withdrawAmount);
            return;
        }


        System.out.printf("Успешно выдано %d денежных средств, купюрами: %n", withdrawAmount);
        withdrawnBanknotes.forEach((k, v) -> System.out.printf("Номинал %s Количество %d%n", k, v));
    }*/

}
