package ru.otus.hw16dbserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw16dbserver.messagesystem.MsClient;
import ru.otus.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class DBServer {
    private static Logger logger = LoggerFactory.getLogger(DBServer.class);

    private MsClient dbServerMsClient;
    private int dbServerPort;


    public DBServer(MsClient dbServerMsClient, @Value("${dbServer.port}") int dbServerPort) {
        this.dbServerMsClient = dbServerMsClient;
        this.dbServerPort = dbServerPort;
    }

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(dbServerPort)) {
            logger.info("Starting DBServer on port: {}", dbServerPort);

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

            dbServerMsClient.handle(receivedMessage);


        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }


}
