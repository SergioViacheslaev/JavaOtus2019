package ru.otus.hw16dbserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw16dbserver.server.DBServer;
import ru.otus.hw16dbserver.services.DBServiceCachedUser;

@SpringBootApplication
public class Hw16DbserverApplication implements CommandLineRunner {

    @Autowired
    private DBServiceCachedUser serviceCachedUser;

    @Autowired
    private DBServer dbServer;

    public static void main(String[] args) {
        SpringApplication.run(Hw16DbserverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        serviceCachedUser.getUsersList().forEach(System.out::println);

        dbServer.go();
    }
}
