package ru.otus.webserver.api.model;

import javax.persistence.*;

/**
 * @author Sergei Viacheslaev
 */
@Entity
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet(String number, User user) {
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
