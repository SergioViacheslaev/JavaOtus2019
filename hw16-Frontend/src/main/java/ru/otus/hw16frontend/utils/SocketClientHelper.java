package ru.otus.hw16frontend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Component
public class SocketClientHelper {
  private static Logger logger = LoggerFactory.getLogger(SocketClientHelper.class);

  private static final int MESSAGE_SERVER_PORT = 8081;
  private static final String MESSAGE_SERVER_HOST = "localhost";


  public void go() {
    try {
      try (Socket clientSocket = new Socket(MESSAGE_SERVER_HOST, MESSAGE_SERVER_PORT)) {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        for (int idx = 0; idx < 3; idx++) {
          logger.info("sending to server");
          out.println("testData:" + idx);
          String resp = in.readLine();
          logger.info("server response: {}", resp);
          sleep();
        }

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
