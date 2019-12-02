package ru.otus.hw16dbserver.messagesystem;


import ru.otus.message.Message;

import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
