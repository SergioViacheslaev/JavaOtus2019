package ru.otus.atmDepartment.bankomats;


import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.FaceValue;
import ru.otus.atmDepartment.withdrawStrategies.WithdrawStrategy;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface ATM  {
    void receiveCash(FaceValue faceValue, int banknotesAmount);

    Map<FaceValue, Integer> giveCash(int cashAmount);

    void printCassetteStorage();

    void showCurrentAtmCashBalance();

    int getAtmCashBalance();

    String getAtmID();

    void setWithdrawStrategy(WithdrawStrategy withdrawStrategy);

    WithdrawStrategy getWithdrawStrategy();

    CassettesStorage getCassettesStorage();

    void setAtmStartState();

    void restoreAtmToStartState();

}
