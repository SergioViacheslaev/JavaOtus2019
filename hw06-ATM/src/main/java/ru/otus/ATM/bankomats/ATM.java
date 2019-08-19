package ru.otus.ATM.bankomats;

import ru.otus.ATM.AtmStatus;
import ru.otus.ATM.Cassette;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.WithdrawStrategies.CashWithdrawStrategy;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Sergei Viacheslaev
 */
public abstract class ATM implements AtmStatus {
    private Map<Integer, Cassette> cassettesStorage = new TreeMap<>(); // номинал/кассета
    private CashWithdrawStrategy cashWithdrawStrategy;


    public CashWithdrawStrategy getCashWithdrawStrategy() {
        return cashWithdrawStrategy;
    }

    public Map<Integer, Cassette> getCassettesStorage() {
        return cassettesStorage;
    }

    public void setCashWithdrawStrategy(CashWithdrawStrategy cashWithdrawStrategy) {
        this.cashWithdrawStrategy = cashWithdrawStrategy;
    }


    public abstract void receiveCash(FaceValue faceValue, int cashAmount);

    public Map<FaceValue, Integer> giveCash(int cashAmount) {
        return cashWithdrawStrategy.withdrawCash(cashAmount);
    }


}
