package ru.otus.orm.model;

import ru.otus.orm.annotations.Id;

/**
 * @author Sergei Viacheslaev
 */
public class Account {
    @Id
    private long no;
    private String type;
    private double rest;

    public Account(String type, double rest) {
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
