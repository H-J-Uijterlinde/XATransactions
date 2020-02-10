package com.semafoor.xa.config;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.semafoor.xa.services")
public class XATransactionConfig {

    @Bean(initMethod = "init", destroyMethod = "shutdownForce")
    public UserTransactionService userTransactionService() {
        Properties properties = new Properties();
        properties.setProperty("com.atomikos.icatch.service", "com.atomikos.icatch.standalone.UserTransactionServiceFactory");
        return new UserTransactionServiceImp(properties);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @DependsOn("userTransactionService")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setStartupTransactionService(false);
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean
    @DependsOn("userTransactionService")
    public UserTransaction userTransaction() {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        try {
            userTransactionImp.setTransactionTimeout(300);
            return userTransactionImp;
        } catch (SystemException e) {
            log.error("Failed setting transaction timeout", e);
            return null;
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(atomikosTransactionManager());
        jtaTransactionManager.setUserTransaction(userTransaction());
        return jtaTransactionManager;
    }
}
