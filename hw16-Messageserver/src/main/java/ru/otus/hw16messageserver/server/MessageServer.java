package ru.otus.hw16messageserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServer {
  private static Logger logger = LoggerFactory.getLogger(MessageServer.class);
  private static final int PORT = 8081;

  public static void main(String[] args) {
    new MessageServer().go();
  }

  public void go() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (!Thread.currentThread().isInterrupted()) {
        logger.info("waiting for client connection");
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
