package ru.otus.atmDepartment.visitors;

import ru.otus.atmDepartment.AtmDepartment;

/**
 * @author Sergei Viacheslaev
 */
public interface Visitor {
    void visit(AtmDepartment atmDepartment);
}
