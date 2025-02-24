package com.journal.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JournalConfigurations {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
