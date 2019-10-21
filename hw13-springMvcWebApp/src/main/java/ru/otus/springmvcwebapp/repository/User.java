package ru.otus.springmvcwebapp.repository;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - User
 *
 * @author Sergei Viacheslaev
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;
    private String lastName;
    private int age;

//    @OneToOne(cascade = CascadeType.ALL)
//    private AddressDataSet address;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<PhoneDataSet> phones = new ArrayList<>();


    public User(String userName, String lastName, int age) {
        this.userName = userName;
        this.lastName = lastName;
        this.age = age;
    }


}
