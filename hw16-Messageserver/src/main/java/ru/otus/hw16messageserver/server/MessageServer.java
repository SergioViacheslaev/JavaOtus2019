package ru.otus.hw16messageserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw16messageserver.messagesystem.MessageSystem;
import ru.otus.hw16messageserver.messagesystem.common.Serializers;
import ru.otus.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MessageServer {
    private static Logger logger = LoggerFactory.getLogger(MessageServer.class);

    @Value("${messageServer.port}")
    private int messageServerPort;

    @Autowired
    private MessageSystem messageSystem;


    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(messageServerPort)) {
            logger.info("Starting MessageServer on port: {}", messageServerPort);
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Waiting for client connection...");
                try (Socket clientSocket = serverSocket.accept()) {
                    clientHandler(clientSocket);
                }
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void clientHandler(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            Message receivedMessage = (Message) ois.readObject();

            logger.info("from client: {} ", receivedMessage);

            String userData = Serializers.deserialize(receivedMessage.getPayload(), String.class);

            logger.info("UserData: {} ", userData);


            messageSystem.newMessage(receivedMessage);



         /*   String input = null;
            while (!"stop".equals(input)) {

                input = in.readLine();

                if (input != null) {
                    logger.info("from client: {} ", input);
                    out.println("echo:" + input);
                }
            }*/


        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }


}
