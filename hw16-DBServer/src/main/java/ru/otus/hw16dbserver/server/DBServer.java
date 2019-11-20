package ru.otus.hw16dbserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class DBServer {
    private static Logger logger = LoggerFactory.getLogger(DBServer.class);

    @Value("${dbServer.port}")
    private int dbServerPort;


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
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    logger.info("from client: {} ", input);
                    out.println("echo:" + input);
                }
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }


}
