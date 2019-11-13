package ru.otus.springmvcwebapp.hibernate.handlers;

import com.google.gson.Gson;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.messagesystem.common.Serializers;
import ru.otus.springmvcwebapp.messagesystem.Message;
import ru.otus.springmvcwebapp.messagesystem.MessageType;
import ru.otus.springmvcwebapp.messagesystem.RequestHandler;
import ru.otus.springmvcwebapp.repository.User;
import java.util.Optional;

public class GetUserDataRequestHandler implements RequestHandler {
    private final DBServiceCachedUser dbService;
    private final Gson gson = new Gson();

    public GetUserDataRequestHandler(DBServiceCachedUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        String jsonUserData = Serializers.deserialize(msg.getPayload(), String.class);

        //Получив сообщение, мы сохраняем пользователя, создав объект User из JSON строки
        User newUser = gson.fromJson(jsonUserData, User.class);
        long savedUserID = dbService.saveUser(newUser);

        //После сохранения user-у нас есть его ID, по котому мы получим данные из кеша
        User cachedUser = dbService.getUser(savedUserID).get();

        //Формируем новый JSON и сообщение для отправки во фронт-сервис
        String savedUserData = gson.toJson(cachedUser);

        return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()),
                MessageType.USER_DATA.getValue(), Serializers.serialize(savedUserData)));
    }
}
