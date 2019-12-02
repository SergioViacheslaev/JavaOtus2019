package ru.otus.hw16dbserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw16dbserver.server.DBServer;

@SpringBootApplication
public class Hw16DbserverApplication implements CommandLineRunner {
    private DBServer dbServer;

    public Hw16DbserverApplication(DBServer dbServer) {
        this.dbServer = dbServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Hw16DbserverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        dbServer.go();
    }
}
