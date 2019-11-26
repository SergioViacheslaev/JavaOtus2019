package ru.otus.hw16frontend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw16frontend.socketserver.FrontendSocketServer;

@SpringBootApplication
public class Hw16FrontendApplication implements CommandLineRunner {
    private FrontendSocketServer frontendSocketServer;

    public Hw16FrontendApplication(FrontendSocketServer frontendSocketServer) {
        this.frontendSocketServer = frontendSocketServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Hw16FrontendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        frontendSocketServer.go();
    }
}
