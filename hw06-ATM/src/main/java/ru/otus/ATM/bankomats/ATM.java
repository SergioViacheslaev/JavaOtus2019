package ru.otus.ATM.bankomats;

import ru.otus.ATM.FaceValue;

import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public abstract class ATM implements AtmStatus {

    public abstract void receiveCash(FaceValue faceValue, int banknotesAmount);

    public abstract Map<FaceValue, Integer> giveCash(int cashAmount);


}
