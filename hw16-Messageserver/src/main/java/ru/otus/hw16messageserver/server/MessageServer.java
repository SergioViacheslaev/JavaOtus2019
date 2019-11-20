package ru.otus.hw16messageserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MessageServer {
    private static Logger logger = LoggerFactory.getLogger(MessageServer.class);

    @Value("${messageServer.port}")
    private int MessageServerPort;


    @PostConstruct
    private void startMessageServer() {
        go();
    }

    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(MessageServerPort)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Waiting for client connection on port: {}", MessageServerPort);
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
