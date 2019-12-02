package ru.otus.hw16dbserver.messagesystem.handlers;

import com.google.gson.Gson;
import ru.otus.hw16dbserver.entity.User;
import ru.otus.hw16dbserver.messagesystem.MessageType;
import ru.otus.hw16dbserver.messagesystem.RequestHandler;
import ru.otus.hw16dbserver.messagesystem.common.Serializers;
import ru.otus.hw16dbserver.services.DBServiceCachedUser;
import ru.otus.message.Message;

import java.util.List;
import java.util.Optional;

public class GetUsersListRequestHandler implements RequestHandler {
    private final DBServiceCachedUser dbService;
    private final Gson gson = new Gson();

    public GetUsersListRequestHandler(DBServiceCachedUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<User> usersList = dbService.getUsersList();
        String jsonUsersList = gson.toJson(usersList);


        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
                MessageType.USER_DATA.getValue(), Serializers.serialize(jsonUsersList)));
    }
}
