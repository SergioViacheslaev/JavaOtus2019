package ru.otus.hw16messageserver.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16messageserver.messagesystem.MessageSystem;
import ru.otus.hw16messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messagesystem.MsClient;
import ru.otus.hw16messageserver.messagesystem.MsClientImpl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Value("${frontend.host}")
    private String frontendHost;
    @Value("${frontend.port}")
    private int frontendPort;
    @Value("${dbServer.host}")
    private String dbServerHost;
    @Value("${dbServer.port}")
    private int dbServerPort;


    @Bean
    public MessageSystem messageSystem() {
        var messageSystem = new MessageSystemImpl();
        return messageSystem;
    }


    @Bean
    public MsClient frontendMsClient() {
        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem());
        frontendMsClient.setClientHost(frontendHost);
        frontendMsClient.setClientPort(frontendPort);
        return frontendMsClient;
    }


    @Bean
    public MsClient databaseMsClient() {
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem());
        databaseMsClient.setClientHost(dbServerHost);
        databaseMsClient.setClientPort(dbServerPort);
        return databaseMsClient;
    }

    @PostConstruct
    private void postConstruct() {
//        frontendMsClient().addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService()));
//        databaseMsClient().addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceCachedUser));

        messageSystem().addClient(frontendMsClient());
        messageSystem().addClient(databaseMsClient());

    }

    @PreDestroy
    private void preDestroy() {
        try {
            messageSystem().dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
