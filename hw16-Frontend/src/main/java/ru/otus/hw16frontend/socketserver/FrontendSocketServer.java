package ru.otus.hw16frontend.socketserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw16frontend.services.frontendservice.FrontendService;
import ru.otus.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class FrontendSocketServer {
    private static Logger logger = LoggerFactory.getLogger(FrontendSocketServer.class);


    private FrontendService frontendService;

    private int frontendSocketServerPort;

    public FrontendSocketServer(@Value("${frontendSocketServer.port}") int frontendSocketServerPort, FrontendService frontendService) {
        this.frontendSocketServerPort = frontendSocketServerPort;
        this.frontendService = frontendService;
    }

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(frontendSocketServerPort)) {
            logger.info("Starting frontendServerSocket on port: {}", frontendSocketServerPort);

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
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            Message receivedMessage = (Message) ois.readObject();

            logger.info("Received from {} message ID[{}]", receivedMessage.getFrom(), receivedMessage.getId());

            frontendService.sendFrontMessage(receivedMessage);


        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }


}
