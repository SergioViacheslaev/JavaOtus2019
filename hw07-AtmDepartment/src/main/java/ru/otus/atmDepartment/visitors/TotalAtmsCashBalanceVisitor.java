package ru.otus.atmDepartment.visitors;

import ru.otus.atmDepartment.bankomats.ATM;

/**
 * @author Sergei Viacheslaev
 */
public class TotalAtmsCashBalanceVisitor implements Visitor<Integer> {

    @Override
    public Integer visit(ATM atm) {
        return atm.getAtmCashBalance();
    }
}
