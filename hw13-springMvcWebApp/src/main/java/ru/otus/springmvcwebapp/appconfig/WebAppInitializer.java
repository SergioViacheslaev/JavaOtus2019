package ru.otus.springmvcwebapp.appconfig;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.otus.springmvcwebapp.appconfig.hibernate.HibernateConfig;
import ru.otus.springmvcwebapp.appconfig.security.WebAppSecurityConfig;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{HibernateConfig.class, WebAppSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        var encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return new Filter[]{encodingFilter};
    }

}
