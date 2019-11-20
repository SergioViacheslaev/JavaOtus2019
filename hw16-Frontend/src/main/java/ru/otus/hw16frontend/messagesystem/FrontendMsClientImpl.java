package ru.otus.hw16frontend.messagesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.hw16frontend.messagesystem.common.Serializers;
import ru.otus.hw16frontend.utils.SocketClientMessageSystem;
import ru.otus.message.Message;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FrontendMsClientImpl implements MsClient {
    private static final Logger logger = LoggerFactory.getLogger(FrontendMsClientImpl.class);

    private final String name;

    private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();

    private SocketClientMessageSystem socketClientMS;

    public FrontendMsClientImpl(@Value("${frontendServiceClientName}") String msClientName, SocketClientMessageSystem socketClientMS) {
        this.name = msClientName;
        this.socketClientMS = socketClientMS;
    }

    @Override
    public void addHandler(MessageType type, RequestHandler requestHandler) {
        this.handlers.put(type.getValue(), requestHandler);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean sendMessage(Message msg) {
        socketClientMS.sendMessage(msg);

        return true;
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlers.get(msg.getType());
            if (requestHandler != null) {
                requestHandler.handle(msg).ifPresent(this::sendMessage);
            } else {
                logger.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, null, msgType.getValue(), Serializers.serialize(data));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontendMsClientImpl that = (FrontendMsClientImpl) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(handlers, that.handlers) &&
                Objects.equals(socketClientMS, that.socketClientMS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, handlers, socketClientMS);
    }
}
