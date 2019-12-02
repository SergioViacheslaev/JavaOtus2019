package ru.otus.hw16messageserver.messagesystem;

import ru.otus.message.Message;

/**
 * @author Sergei Viacheslaev
 */
public interface SendMessageHandler {
    void handle(Message message, String host, int port);
}
