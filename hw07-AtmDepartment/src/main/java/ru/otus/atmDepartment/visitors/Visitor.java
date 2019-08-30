package ru.otus.atmDepartment.visitors;

import ru.otus.atmDepartment.bankomats.ATM;

/**
 * @author Sergei Viacheslaev
 */
public interface Visitor {
    void visit(ATM atm);
}
