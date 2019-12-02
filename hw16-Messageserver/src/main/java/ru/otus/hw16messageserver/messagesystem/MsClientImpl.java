package ru.otus.hw16messageserver.messagesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16messageserver.messagesystem.common.Serializers;
import ru.otus.hw16messageserver.utils.SocketClientMessageSystem;
import ru.otus.message.Message;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class MsClientImpl implements MsClient {
    private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

    private String name;
    private String host;
    private int port;

    private final MessageSystem messageSystem;

    private SocketClientMessageSystem socketClient;

    private final Map<String, SendMessageHandler> handlers = new ConcurrentHashMap<>();

    public MsClientImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.socketClient = new SocketClientMessageSystem();
    }

    @PostConstruct
    private void postConstruct() {
        messageSystem.addClient(this);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addHandler(MessageType type, SendMessageHandler sendMessageHandler) {
        this.handlers.put(type.getValue(), sendMessageHandler);
    }


    @Override
    public boolean sendMessage(Message msg) {
        boolean result = messageSystem.newMessage(msg);
        if (!result) {
            logger.error("the last message was rejected: {}", msg);
        }
        return result;
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        socketClient.sendMessage(msg, host, port);

    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, null, msgType.getValue(), Serializers.serialize(data));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientImpl msClient = (MsClientImpl) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }


}
