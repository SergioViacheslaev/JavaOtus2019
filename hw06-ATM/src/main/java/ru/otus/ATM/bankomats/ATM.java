package ru.otus.ATM.bankomats;

import ru.otus.ATM.AtmStatus;
import ru.otus.ATM.Cassette.AtmCassette;
import ru.otus.ATM.Cassette.Cassette;
import ru.otus.ATM.Cassette.CassettesStorage;
import ru.otus.ATM.FaceValue;
import ru.otus.ATM.WithdrawStrategies.CashWithdrawStrategy;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Sergei Viacheslaev
 */
public abstract class ATM implements AtmStatus {
    private CashWithdrawStrategy cashWithdrawStrategy;



    public CashWithdrawStrategy getCashWithdrawStrategy() {
        return cashWithdrawStrategy;
    }


    public void setCashWithdrawStrategy(CashWithdrawStrategy cashWithdrawStrategy) {
        this.cashWithdrawStrategy = cashWithdrawStrategy;
    }

    public abstract void receiveCash(FaceValue faceValue, int banknotesAmount) ;



    public Map<FaceValue, Integer> giveCash(int cashAmount) {
        return cashWithdrawStrategy.withdrawCash(cashAmount);
    }


}
