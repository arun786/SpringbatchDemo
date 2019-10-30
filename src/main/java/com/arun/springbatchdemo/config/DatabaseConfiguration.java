package com.arun.springbatchdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(value = "com.arun.springbatchdemo", entityManagerFactoryRef = "batchEntityManagerFactory")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean(name = "dataSource")
    public DataSource batchDataSource(){
        return null;
    }

    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager transactionManager(){
        return null;
    }
}
