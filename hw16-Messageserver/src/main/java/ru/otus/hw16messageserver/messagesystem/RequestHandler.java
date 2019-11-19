package ru.otus.hw16messageserver.messagesystem;


import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
