package ru.otus.hw16messageserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw16messageserver.server.MessageServer;

@SpringBootApplication
public class Hw16MessageserverApplication implements CommandLineRunner {

    @Autowired
    private MessageServer messageServer;

    public static void main(String[] args) {

        SpringApplication.run(Hw16MessageserverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        messageServer.go();

    }
}
