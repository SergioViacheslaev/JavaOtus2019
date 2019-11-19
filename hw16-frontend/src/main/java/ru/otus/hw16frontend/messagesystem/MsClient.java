package ru.otus.hw16frontend.messagesystem;

public interface MsClient {

  void addHandler(MessageType type, RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, T data, MessageType msgType);

}
