package ru.otus.hw16messageserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw16MessageserverApplication  {
    private static Logger logger = LoggerFactory.getLogger(Hw16MessageserverApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(Hw16MessageserverApplication.class, args);
    }

/*
    @Override
    public void run(String... args) {
        logger.info("MessageServer is starting...");

        new MessageServer().go();
    }*/
}
