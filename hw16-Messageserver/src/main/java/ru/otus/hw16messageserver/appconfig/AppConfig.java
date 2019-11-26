package ru.otus.hw16messageserver.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16messageserver.messagesystem.MessageSystem;
import ru.otus.hw16messageserver.messagesystem.MsClient;
import ru.otus.hw16messageserver.messagesystem.MsClientImpl;


@Configuration
@RequiredArgsConstructor
public class AppConfig {


    @Bean
    @ConfigurationProperties(prefix = "frontend")
    public MsClient frontendMsClient(MessageSystem messageSystem) {
        var frontendMsClient = new MsClientImpl(messageSystem);
        return frontendMsClient;
    }


    @Bean
    @ConfigurationProperties(prefix = "dbserver")
    public MsClient databaseMsClient(MessageSystem messageSystem) {
        var databaseMsClient = new MsClientImpl(messageSystem);
        return databaseMsClient;
    }


}
