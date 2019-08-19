package ru.otus.ATM.WithdrawStrategies;

import ru.otus.ATM.FaceValue;

import java.util.Map;

/**
 * Стратегия выдачи денег
 *
 * @author Sergei Viacheslaev
 */
public interface CashWithdrawStrategy {
    Map<FaceValue, Integer> withdrawCash(int cashAmount);
}
