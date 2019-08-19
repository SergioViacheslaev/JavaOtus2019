package ru.otus.ATM;

/**
 * @author Sergei Viacheslaev
 */
public class Cassette {
    private FaceValue cassetteFacevalue;
    private int banknotesAmount;
    private int savedBanknotesAmount;


    public Cassette(FaceValue cassetteFacevalue, int banknotesAmount) {
        this.cassetteFacevalue = cassetteFacevalue;
        this.banknotesAmount = banknotesAmount;
    }


    public void setBanknotesAmount(int banknotesAmount) {
        this.banknotesAmount = banknotesAmount;
    }

    public int getBanknotesAmount() {
        return banknotesAmount;
    }

    public int getCassetteCashBalance() {
        return cassetteFacevalue.getIntValue() * banknotesAmount;
    }

    public FaceValue getCassetteFacevalue() {
        return cassetteFacevalue;
    }

    public boolean hasBanknotes() {
        return banknotesAmount > 0;
    }

    public void saveBanknotesAmount() {
        savedBanknotesAmount = banknotesAmount;
    }

    public void restoreBaknotesAmount() {
        banknotesAmount = savedBanknotesAmount;
    }

    public void decrementBanknotesAmount() {
        banknotesAmount--;
    }


    @Override
    public String toString() {
        return "Номинал кассеты " + cassetteFacevalue + " Количество банкнот " + banknotesAmount;
    }


/*   public int getBanknotesAmount() {
        return banknotesAmount;
    }

    public FaceValue getCassetteFaceValue() {
        return cassetteFaceValue;
    }

    public void setBanknotesAmount(int banknotesAmount) {
        this.banknotesAmount = banknotesAmount;
    }

    //Количество денег в кассете
    public int currentMoneyAmount() {
        return banknotesAmount * cassetteFaceValue.getIntValue();
    }

    //Проверка остатка
    boolean hasBanknotes() {
        return getBanknotesAmount() > 0;
    }

    //Уменьшение остатка
    public void decrementAmount() {
        if (banknotesAmount > 0) {
            banknotesAmount--;
        }
    }

    //Временное сохранение кол-ва банкнот
    public void savebanknotesAmount() {
        temp_banknotesAmount = banknotesAmount;
    }*/
}
