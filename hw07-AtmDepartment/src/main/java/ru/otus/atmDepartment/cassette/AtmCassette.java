package ru.otus.atmDepartment.cassette;


import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.FaceValue;

import java.io.Serializable;


/**
 * @author Sergei Viacheslaev
 */
public final class AtmCassette implements Serializable {
    private final int CASSETTE_MAX_CAPACITY = 1000; //вместимость кассеты (MAX кол-во банкот)
    private final FaceValue CASSETTE_FACEVALUE;

    private int cassetteBanknotesAmount;
    private int savedBanknotesAmount;


    public AtmCassette(FaceValue cassetteFacevalue, int banknotesAmount) throws CassetteOutOfAmountException {
        if (banknotesAmount < 0 || banknotesAmount > CASSETTE_MAX_CAPACITY) {

            throw new CassetteOutOfAmountException("Неверно задано кол-во банкнот при создании кассеты");

        }
        this.cassetteBanknotesAmount = banknotesAmount;
        this.CASSETTE_FACEVALUE = cassetteFacevalue;

    }

    @Override
    public AtmCassette clone() throws CloneNotSupportedException {
        return (AtmCassette) super.clone();
    }

    @Override
    public String toString() {
        return "Номинал кассеты " + CASSETTE_FACEVALUE + " Количество банкнот " + cassetteBanknotesAmount;
    }


    public void setCassetteBanknotesAmount(int cassetteBanknotesAmount) {
        this.cassetteBanknotesAmount = cassetteBanknotesAmount;
    }

    public int getCassetteBanknotesAmount() {
        return cassetteBanknotesAmount;
    }

    public int getCassetteCashBalance() {
        return CASSETTE_FACEVALUE.getIntValue() * cassetteBanknotesAmount;
    }

    public FaceValue getCASSETTE_FACEVALUE() {
        return CASSETTE_FACEVALUE;
    }

    public boolean hasBanknotes() {
        return cassetteBanknotesAmount > 0;
    }

    public void saveBanknotesAmount() {
        savedBanknotesAmount = cassetteBanknotesAmount;
    }

    public void restoreBaknotesAmount() {
        cassetteBanknotesAmount = savedBanknotesAmount;
    }

    public void decrementBanknotesAmount() {
        cassetteBanknotesAmount--;
    }

    public boolean isCassetteFull() {

        return cassetteBanknotesAmount >= CASSETTE_MAX_CAPACITY;

    }

    public int getCassetteFreeSlots() {
        return CASSETTE_MAX_CAPACITY - cassetteBanknotesAmount;
    }


}
