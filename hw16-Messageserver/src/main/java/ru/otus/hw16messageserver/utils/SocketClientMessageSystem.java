package ru.otus.hw16messageserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.message.Message;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Component
public class SocketClientMessageSystem {
    private static Logger logger = LoggerFactory.getLogger(SocketClientMessageSystem.class);


    public void sendMessage(Message message, String clientHost, int clientPort) {

        try (Socket clientSocket = new Socket(clientHost, clientPort);
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            oos.writeObject(message);
            logger.info("Message with ID [{}] is send to {}", message.getId(), message.getTo());

            sleep();


        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
