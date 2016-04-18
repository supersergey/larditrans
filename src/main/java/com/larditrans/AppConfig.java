package com.larditrans;

import com.larditrans.dao.EntryDao;
import com.larditrans.dao.EntryDaoImplDB;
import com.larditrans.dao.UserDao;
import com.larditrans.dao.UserDaoImplDB;
import com.larditrans.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by sergey on 14.04.2016.
 */
@Configuration
@ComponentScan("com.larditrans")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public EntityManager entityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LardiTransJPA");
        return emf.createEntityManager();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImplDB();
    }

    @Bean
    public EntryDao entryDao() {
        return new EntryDaoImplDB();
    }

    @Bean
    public UserService userService()
    {
        return new UserService();
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**").addResourceLocations("WEB-INF/pages/static/");
    }
}

