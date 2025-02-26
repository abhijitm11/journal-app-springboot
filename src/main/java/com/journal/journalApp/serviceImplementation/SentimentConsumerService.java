package com.journal.journalApp.serviceImplementation;

import com.journal.journalApp.entity.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentConsumerService {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @KafkaListener(topics = "weekly-sentiment", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        log.info("Consumed in Kafka! {}", sentimentData);
        //emailService.sendMail(sentimentData.getEmail(), "Sentiment for past week!", sentimentData.getSentiment());
    }
}
