package ru.otus.hw16frontend.repository;


import com.google.gson.annotations.Expose;
import lombok.*;

/**
 * Entity - User
 *
 * @author Sergei Viacheslaev
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Expose(deserialize = false)
    private long id;

    private String firstName;
    private String lastName;
    private int age;

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

}
