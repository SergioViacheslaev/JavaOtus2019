package ru.otus.springmvcwebapp.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.front.FrontendService;
import ru.otus.springmvcwebapp.front.FrontendServiceImpl;
import ru.otus.springmvcwebapp.front.handlers.GetUserDataResponseHandler;
import ru.otus.springmvcwebapp.hibernate.handlers.GetUserDataRequestHandler;
import ru.otus.springmvcwebapp.messagesystem.*;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;

@EnableWebMvc
@Configuration
@ComponentScan("ru.otus.springmvcwebapp")
@RequiredArgsConstructor
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    private DBServiceCachedUser dbServiceCachedUser;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    ApplicationContext applicationContext;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        var viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


    @Bean
    public MessageSystem messageSystem() {
        var messageSystem = new MessageSystemImpl();
        return messageSystem;
    }


    @Bean
    public MsClient frontendMsClient() {
        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem());
        return frontendMsClient;
    }

    @Bean
    public FrontendService frontendService() {
        var frontendService = new FrontendServiceImpl(frontendMsClient(), DATABASE_SERVICE_CLIENT_NAME);
        return frontendService;
    }

    @Bean
    public MsClient databaseMsClient() {
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem());
        return databaseMsClient;
    }

    @PostConstruct
    private void postConstruct() {
        frontendMsClient().addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService()));
        databaseMsClient().addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceCachedUser));

        messageSystem().addClient(frontendMsClient());
        messageSystem().addClient(databaseMsClient());

        //Init cache and DB
        dbServiceCachedUser.saveUser(new User("Vasya", "Pupkin", 22));
        dbServiceCachedUser.saveUser(new User("Tom", "Hanks", 65));
        dbServiceCachedUser.saveUser(new User("Bill", "Gates", 51));
        dbServiceCachedUser.saveUser(new User("Maulder", "Fox", 35));
    }




    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/**").addResourceLocations("/resources/static/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/")
                .resourceChain(false).addResolver(new WebJarsResourceResolver());
    }


}
