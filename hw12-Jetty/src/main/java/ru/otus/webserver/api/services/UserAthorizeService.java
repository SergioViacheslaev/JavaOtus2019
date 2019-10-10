package ru.otus.webserver.api.services;

/**
 * @author Sergei Viacheslaev
 */
public class UserAthorizeService {
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";


    public boolean authorizeUser(String username, String password) {
        return (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD));
    }

}
