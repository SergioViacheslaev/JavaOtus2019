package ru.otus.hw16dbserver.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16dbserver.messagesystem.DBServerMsClientImpl;
import ru.otus.hw16dbserver.messagesystem.MessageType;
import ru.otus.hw16dbserver.messagesystem.MsClient;
import ru.otus.hw16dbserver.messagesystem.handlers.GetUserDataRequestHandler;
import ru.otus.hw16dbserver.services.DBServiceCachedUser;
import ru.otus.hw16dbserver.utils.SocketClientDBServer;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Autowired
    private DBServiceCachedUser serviceCachedUser;

    @Value("${databaseServiceClientName}")
    private String dbServerMsClientName;

    @Value("${messageServer.port}")
    private int messageServerPort;
    @Value("${messageServer.host}")
    private String messageServerHost;


    @Bean
    public SocketClientDBServer socketClientDBServer() {
        return new SocketClientDBServer();
    }


    @Bean
    public MsClient dbServerMsClientImpl() {
        var dbServerMsClient = new DBServerMsClientImpl(dbServerMsClientName, socketClientDBServer());
        dbServerMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(serviceCachedUser));
        return dbServerMsClient;
    }


}
