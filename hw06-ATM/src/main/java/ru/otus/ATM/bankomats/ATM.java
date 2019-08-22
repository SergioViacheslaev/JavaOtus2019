package ru.otus.ATM.bankomats;

import ru.otus.ATM.FaceValue;

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

}
