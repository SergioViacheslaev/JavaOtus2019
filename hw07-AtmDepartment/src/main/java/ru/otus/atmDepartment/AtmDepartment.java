package ru.otus.atmDepartment;

import ru.otus.atmDepartment.bankomats.ATM;
import ru.otus.atmDepartment.bankomats.BankomatSber;
import ru.otus.atmDepartment.visitors.TotalAtmsCashBalanceVisitor;
import ru.otus.atmDepartment.visitors.Visitor;
import ru.otus.atmDepartment.withdrawStrategies.MinimumBanknotesWithdrawStrategy;
import ru.otus.atmDepartment.withdrawStrategies.WithdrawStrategy;
import ru.otus.atmDepartment.exceptions.NoSuchAtmException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
public class AtmDepartment {
    private List<ATM> atmList = new ArrayList<>();
    private final int DEFAULT_ATM_NUMBER = 5;
    private final WithdrawStrategy DEFAULT_WITHDRAW_STRATEGY = new MinimumBanknotesWithdrawStrategy();
    private int totalAtmsCashAmount;

    public AtmDepartment() {
        for (int i = 0; i < DEFAULT_ATM_NUMBER; i++) {
            BankomatSber bankomatSber = new BankomatSber(String.format("SberATM#%d", i + 1), DEFAULT_WITHDRAW_STRATEGY);
            atmList.add(bankomatSber);
        }
    }

    public void setTotalAtmsCashAmount(int totalAtmsCashAmount) {
        this.totalAtmsCashAmount = totalAtmsCashAmount;
    }

    public void addAtm(ATM atm) {
        atmList.add(atm);
    }

    public List<ATM> getAtmList() {
        return atmList;
    }

    public ATM getAtm(String atmID) {
        ATM atmObj = null;
        try {
            atmObj = atmList.stream().filter(atm -> atm.getAtmID().equalsIgnoreCase(atmID))
                    .findFirst().orElseThrow(() -> new NoSuchAtmException(atmID));
        } catch (NoSuchAtmException e) {
            e.printStackTrace();
        }
        return atmObj;

    }


    public int getAtmsTotalCashAmount() {
        acceptVisitor(new TotalAtmsCashBalanceVisitor());
        return totalAtmsCashAmount;
    }

    public void showAtmsTotalCashAmount() {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

        System.out.printf("Баланс наличных денег по всем банкоматам департамента: %s%n",
                formatter.format(getAtmsTotalCashAmount()));
        atmList.forEach(ATM::showCurrentAtmCashBalance);

    }

    public void showAllAtms() {
        atmList.forEach(System.out::println);
    }

    public void restoreAllAtmsToStartState() {
        atmList.forEach(ATM::restoreAtmToStartState);
        System.out.println("Все банкоматы восстановлены в начальное состояние.");
    }

    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }


}
