package com.semafoor.xa.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static javax.persistence.spi.PersistenceUnitTransactionType.JTA;

@Slf4j
@Configuration
@EnableJpaRepositories(basePackages = "com.semafoor.xa.model")
public class XAJpaConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSourceA() {
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        dataSourceBean.setUniqueResourceName("XAdbdsA");
        dataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        dataSourceBean.setPoolSize(2);
        dataSourceBean.setXaProperties(XAdbAProperties());
        return dataSourceBean;
    }

    @Bean
    public Properties XAdbAProperties() {
        Properties properties = new Properties();
        properties.setProperty("databaseName", "XaDbA");
        properties.setProperty("user", "userA");
        properties.setProperty("password", "userA");
        return properties;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSourceB() {
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        dataSourceBean.setUniqueResourceName("XAdbdsB");
        dataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        dataSourceBean.setPoolSize(2);
        dataSourceBean.setXaProperties(XAdbAProperties());
        return dataSourceBean;
    }

    @Bean
    public Properties XAdbBProperties() {
        Properties properties = new Properties();
        properties.setProperty("databaseName", "XaDbB");
        properties.setProperty("user", "userB");
        properties.setProperty("password", "userB");
        return properties;
    }

    @Bean
    @DependsOn("transactionManager")
    public Properties hibernateProperties() {
        Properties properties = new Properties();

        // specific for atomikos JTA platform
        properties.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JTATransactionFactory");
        properties.setProperty("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform");

        // specific for Hibernate 5
        properties.setProperty("hibernate.transaction.coordinator_class", "jta");

        // generic
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("javax.persistence.transactionType", JTA.name());
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.jdbc.batch_size", "50");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public EntityManagerFactory emfA() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("emfA");
        factoryBean.setPackagesToScan("com.semafoor.xa.model");
        factoryBean.setDataSource(dataSourceA());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public EntityManagerFactory emfB() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("emfB");
        factoryBean.setPackagesToScan("com.semafoor.xa.model");
        factoryBean.setDataSource(dataSourceB());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
