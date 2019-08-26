package ru.otus.AtmDepartment.bankomats;


import ru.otus.AtmDepartment.Cassette.CassettesStorage;
import ru.otus.AtmDepartment.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public interface ATM {
    void receiveCash(FaceValue faceValue, int banknotesAmount);

    Map<FaceValue, Integer> giveCash(int cashAmount);

    void printCassetteStorage();

    void showCurrentAtmCashBalance();

    int getAtmCashBalance();

    String getAtmID();

    void setWithdrawStrategy(WithdrawStrategy withdrawStrategy);

    CassettesStorage getCassettesStorage();

}
