package ru.otus.hw16frontend.services.frontendservice;


import ru.otus.message.Message;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void getUserData(long userId, Consumer<String> dataConsumer);

    void saveUser(String frontMessage, Consumer<String> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);

    void sendFrontMessage(Message message);


}

