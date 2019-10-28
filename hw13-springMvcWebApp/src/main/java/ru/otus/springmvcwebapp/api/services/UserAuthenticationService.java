package ru.otus.springmvcwebapp.api.services;

/**
 * @author Sergei Viacheslaev
 */
public class UserAuthenticationService {
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";


    public boolean isUserLoginPasswordCorrect(String username, String password) {
        return (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD));
    }

}
