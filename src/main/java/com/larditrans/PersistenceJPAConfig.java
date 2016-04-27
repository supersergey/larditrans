package com.larditrans;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sergey on 18.04.2016.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.larditrans")
public class PersistenceJPAConfig {
    @Value("${filedb.enabled}")
    private Boolean isFileDbEnabled;
    @Value("${javax.persistence.jdbc.url}")
    private String dbUrl;
    @Value("${javax.persistence.jdbc.user}")
    private String dbUser;
    @Value("${javax.persistence.jdbc.password}")
    private String dbPassword;
    @Value("${javax.persistence.name}")
    private String persistenceName;

    public PersistenceJPAConfig() {
        super();
    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        if (!isFileDbEnabled)
//        {
//            em.setPersistenceUnitName(persistenceName);
//            em.setDataSource( dataSource );
//            em.setPackagesToScan( "com.larditrans" );
//            em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
//            em.setJpaProperties( hibernateProperties );
//            em.setPersistenceUnitName( "LardiTransJPA" );
//            em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//            em.afterPropertiesSet();
//
//            return em.getObject();
//        }
//        else
//            em.setPersistenceUnitName("FileDb");
//        return em.getObject();
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        if (!isFileDbEnabled)
//        {
//            dataSource.setUrl(dbUrl);
//            dataSource.setUsername(dbUser);
//            dataSource.setPassword(dbPassword);
//        }
//        return dataSource;
//    }
//
//    @Bean
//    public Properties hibernateProperties(){
//        final Properties properties = new Properties();
//        properties.put( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect" );
//        properties.put( "hibernate.connection.driver_class", "com.mysql.jdbc.Driver" );
//        properties.put( "hibernate.hbm2ddl.auto", "validate" );
//        properties.put( "hibernate.flushMode", "FLUSH_AUTO" );
//        return properties;
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        if (!isFileDbEnabled) {
            Map<String, String> properties = new HashMap<>();

            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url", dbUrl);
            properties.put("javax.persistence.jdbc.user", dbUser);
            properties.put("javax.persistence.jdbc.password", dbPassword);

            emf.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
            emf.setPackagesToScan("com.larditrans");
            emf.setPersistenceUnitName("LardiTransJPA");
            emf.setJpaPropertyMap(properties);
            emf.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        }
        else
           emf.setPersistenceUnitName("FileDb");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
