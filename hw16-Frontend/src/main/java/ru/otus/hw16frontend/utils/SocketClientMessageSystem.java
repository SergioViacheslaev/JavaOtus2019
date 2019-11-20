package ru.otus.hw16frontend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Component
public class SocketClientMessageSystem {
    private static Logger logger = LoggerFactory.getLogger(SocketClientMessageSystem.class);

    @Value("${messageServer.port}")
    private int messageServerPort;
    @Value("${messageServer.host}")
    private String messageServerHost;


    public void sendMessage(Message message) {
        try {
            try (Socket clientSocket = new Socket(messageServerHost, messageServerPort);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {


                oos.writeObject(message);
                logger.info("Message is send to MessageServer");

                sleep();

                logger.info("stop communication");
                out.println("stop");


            }

        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
