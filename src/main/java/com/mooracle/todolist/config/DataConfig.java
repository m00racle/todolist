package com.mooracle.todolist.config;


import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration//<- spring to know this is a config file
@EnableJpaRepositories(basePackages = "com.mooracle.todolist.dao")//<- locate the dao package
@PropertySource("application.properties")//<- locate the root classpath properties file
public class DataConfig {
    //field declaratrion:
    @Autowired
    private Environment environment;//<- choose the springframework.core.env

    @Bean //this @Bean must be named entityManagerFactory by the weaver, the system will seek specifically this name!!
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        //hibernate Java Persistent API adaptor
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(environment.getProperty("todolist.entity.package"));
        factoryBean.setJpaProperties(getHibernateProperties());

        return factoryBean;
    }

    @Bean
    public Properties getHibernateProperties() {
        //the getHibernateProperties substitute the hibernate.cfg.xml function to set and configure various hibernate
        //properties
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.implicit_naming_strategy", environment.getProperty("hibernate.implicit_naming_strategy"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("todolist.db.driver"));
        dataSource.setUrl(environment.getProperty("todolist.db.url"));
        dataSource.setUsername(environment.getProperty("todolist.db.username"));
        dataSource.setPassword(environment.getProperty("todolist.db.password"));

        return dataSource;
    }
}
