package ru.otus.hw16messageserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw16MessageserverApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Hw16MessageserverApplication.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("Hello world ! ");

    }
}
