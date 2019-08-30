package ru.otus.atmDepartment.visitors;

import ru.otus.atmDepartment.bankomats.ATM;

/**
 * @author Sergei Viacheslaev
 */
public class TotalAtmsCashBalanceVisitor implements Visitor {
    private static int totalAtmsCashBalance;

    @Override
    public void visit(ATM atm) {
        totalAtmsCashBalance += atm.getAtmCashBalance();
    }

    public int getTotalAtmsCashBalance() {
        int totalCashAmount = totalAtmsCashBalance;
        totalAtmsCashBalance = 0;
        return totalCashAmount;
    }
}
