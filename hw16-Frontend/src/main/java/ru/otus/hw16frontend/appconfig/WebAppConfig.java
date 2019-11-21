package ru.otus.hw16frontend.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@Configuration
@RequiredArgsConstructor
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/")
                .resourceChain(false).addResolver(new WebJarsResourceResolver());
    }


}
