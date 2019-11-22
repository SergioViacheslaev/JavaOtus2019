package ru.otus.hw16frontend.services.frontendservice;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw16frontend.messagesystem.MessageType;
import ru.otus.hw16frontend.messagesystem.MsClient;
import ru.otus.hw16frontend.messagesystem.common.Serializers;
import ru.otus.message.Message;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);
    private final JsonParser jsonParser = new JsonParser();

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, @Value("${databaseServiceClientName}") String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUsersList(String frontMessage, Consumer<String> dataConsumer) {
        //Формируем Message
        Message outMsg = msClient.produceMessage(databaseServiceClientName, "", MessageType.USERS_LIST);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }


    @Override
    public void saveUser(String frontMessage, Consumer<String> dataConsumer) {
        //Извлекаем JSON объект с данными пользователя из сообщения
        JsonObject jsonObject = jsonParser.parse(frontMessage).getAsJsonObject();
        logger.info("jsonObject: {}", jsonObject);

        JsonObject messageStr = jsonObject.getAsJsonObject("messageStr");
        logger.info("messageStr: {}", messageStr);

        String jsonUserData = messageStr.toString();

        //Формируем Message
        Message outMsg = msClient.produceMessage(databaseServiceClientName, jsonUserData, MessageType.USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }


    @Override
    public void getUserData(long userId, Consumer<String> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, userId, MessageType.USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            logger.warn("consumer not found for:{}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }

    @Override
    public void sendFrontMessage(Message message) {
        logger.info("new message:{}", message);
        try {
            String userJsonData = Serializers.deserialize(message.getPayload(), String.class);
            UUID sourceMessageId = message.getSourceMessageId();
            if (sourceMessageId == null) {
                throw new RuntimeException("Not found sourceMsg for message:" + message.getId());
            }

            takeConsumer(sourceMessageId, String.class).ifPresent(consumer -> consumer.accept(userJsonData));

        } catch (Exception ex) {
            logger.error("msg:" + message, ex);
        }

    }
}
