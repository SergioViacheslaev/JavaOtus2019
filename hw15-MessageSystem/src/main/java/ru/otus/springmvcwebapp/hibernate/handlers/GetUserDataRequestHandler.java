package ru.otus.springmvcwebapp.hibernate.handlers;



import org.springframework.stereotype.Component;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.common.Serializers;
import ru.otus.springmvcwebapp.messagesystem.Message;
import ru.otus.springmvcwebapp.messagesystem.MessageType;
import ru.otus.springmvcwebapp.messagesystem.RequestHandler;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {
  private final DBServiceCachedUser dbService;

  public GetUserDataRequestHandler(DBServiceCachedUser dbService) {
    this.dbService = dbService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    long id = Serializers.deserialize(msg.getPayload(), Long.class);
//    String data = dbService.getUserData(id);
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA.getValue(), Serializers.serialize("data")));
  }
}
