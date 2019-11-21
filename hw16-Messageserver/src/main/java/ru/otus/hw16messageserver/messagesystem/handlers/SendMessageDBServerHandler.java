package ru.otus.hw16messageserver.messagesystem.handlers;

import ru.otus.hw16messageserver.messagesystem.SendMessageHandler;
import ru.otus.hw16messageserver.utils.SocketClientMessageSystem;
import ru.otus.message.Message;

/**
 * @author Sergei Viacheslaev
 */

public class SendMessageDBServerHandler implements SendMessageHandler {


    private SocketClientMessageSystem socketClient;

    public SendMessageDBServerHandler() {
        this.socketClient = new SocketClientMessageSystem();
    }

    @Override
    public void handle(Message message, String host, int port) {
        socketClient.sendMessage(message, host, port);
    }
}
