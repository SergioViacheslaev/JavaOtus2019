package ru.otus.atmDepartment.bankomats;


import ru.otus.atmDepartment.atmStates.AtmStartState;
import ru.otus.atmDepartment.cassette.CassettesStorage;
import ru.otus.atmDepartment.FaceValue;
import ru.otus.atmDepartment.exceptions.CassetteIsFullException;
import ru.otus.atmDepartment.exceptions.CassetteOutOfAmountException;
import ru.otus.atmDepartment.visitors.Visitor;
import ru.otus.atmDepartment.withdrawStrategies.MinimumBanknotesWithdrawStrategy;
import ru.otus.atmDepartment.withdrawStrategies.WithdrawStrategy;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class BankomatSber implements ATM, Serializable {
    private CassettesStorage cassettes_storage = new CassettesStorage();
    private WithdrawStrategy withdrawStrategy;
    private transient String atmID;
    private transient AtmStartState atmStartState;


    public BankomatSber(String atmID) {
        this.atmID = atmID;
        this.withdrawStrategy = new MinimumBanknotesWithdrawStrategy();
        setAtmStartState();
    }

    public BankomatSber(String atmID, WithdrawStrategy withdrawStrategy) {
        this.atmID = atmID;
        this.withdrawStrategy = withdrawStrategy;
        setAtmStartState();

    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    //Установить текущее состояние- начальным
    @Override
    public void setAtmStartState() {
        atmStartState = new AtmStartState(this);

    }

    //Восстановить состояние к начальному
    @Override
    public void restoreAtmToStartState() {
        BankomatSber savedStartState = (BankomatSber) atmStartState.getSavedStartState();
        cassettes_storage = savedStartState.cassettes_storage;
        withdrawStrategy = savedStartState.withdrawStrategy;
    }


    @Override
    public String getAtmID() {
        return atmID;
    }

    @Override
    public WithdrawStrategy getWithdrawStrategy() {
        return withdrawStrategy;
    }

    @Override
    public void setWithdrawStrategy(WithdrawStrategy withdrawStrategy) {
        this.withdrawStrategy = withdrawStrategy;
    }

    @Override
    public CassettesStorage getCassettesStorage() {
        return cassettes_storage;
    }

    @Override
    public void receiveCash(FaceValue faceValue, int banknotesAmount) {
        try {
            cassettes_storage.receiveBanknotes(faceValue, banknotesAmount);
            System.out.printf("Банкомат %s успешно принял сумму %d%n", atmID, banknotesAmount * faceValue.getIntValue());
        } catch (CassetteIsFullException | CassetteOutOfAmountException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<FaceValue, Integer> giveCash(int cashAmount) {
        try {
            return withdrawStrategy.withdrawCash(cassettes_storage, cashAmount);
        } catch (CassetteOutOfAmountException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

    }

    @Override
    public void printCassetteStorage() {
        cassettes_storage.getCassetteStorage().values().forEach(System.out::println);
    }

    @Override
    public void showCurrentAtmCashBalance() {
        System.out.printf("Текущий баланс наличных денег в банкомате %s: %d%n", atmID, getAtmCashBalance());
    }

    @Override
    public int getAtmCashBalance() {
        return cassettes_storage.getCassetesStorageBalance();
    }

    @Override
    public String toString() {
        return atmID;
    }


}
