package ru.otus.ATM.WithdrawStrategies;

import ru.otus.ATM.Cassette.AtmCassette;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.bankomats.ATM;

import java.util.*;

/**Стратегия выдачи наличных крупными купюрами
 *
 * @author Sergei Viacheslaev
 */
public class LargeCashWithdawStrategy implements CashWithdrawStrategy {
    private ATM atm;
    private Map<Integer, AtmCassette> sortedCassettes;

    public LargeCashWithdawStrategy(ATM atm) {
        this.atm = atm;
        sortedCassettes = new TreeMap<>(Comparator.reverseOrder());
       // sortedCassettes.putAll(atm.getCassettesStorage());
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
