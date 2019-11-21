package ru.otus.hw16messageserver.messagesystem;

import ru.otus.message.Message;

public interface MsClient {

  void addHandler(MessageType type, SendMessageHandler sendMessageHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, T data, MessageType msgType);

}
