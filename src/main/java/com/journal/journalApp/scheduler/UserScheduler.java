package com.journal.journalApp.scheduler;

import com.journal.journalApp.cache.AppCache;
import com.journal.journalApp.constants.Sentiment;
import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.entity.SentimentData;
import com.journal.journalApp.entity.User;
import com.journal.journalApp.serviceImplementation.EmailServiceImpl;
import com.journal.journalApp.serviceImplementation.SentimentAnalysisServiceImpl;
import com.journal.journalApp.serviceImplementation.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SentimentAnalysisServiceImpl sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    // @Scheduled(cron = "0 0 9 * * Sun")
    @Scheduled(cron = "0 */45 * * * *")
    public void fetchUsersAndSendSentimentMail() {
        List<User> usersForSA = userService.findUsersForSA();
        for(User user: usersForSA) {
            List<JournalEntry> entries = user.getJournalEntries();
            // get the list of journal entries that were created in last week
            List<Sentiment> filteredEntries = entries.stream()
                    .filter(x-> x.getCreationDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getSentiment).toList();

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: filteredEntries) {
                if(sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int max_count = 0;
            for(Map.Entry<Sentiment, Integer> entry: sentimentCounts.entrySet()) {
                if(entry.getValue() > max_count) {
                    max_count = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null) {
                // log.info("Sentiment for user: {} is: {}", user, mostFrequentSentiment.toString());
                // emailService.sendMail(user.getEmail(), "Sentiment Analysis for last week!", mostFrequentSentiment.toString());
                try {
                    SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment(mostFrequentSentiment.toString()).build();
                    kafkaTemplate.send("weekly-sentiment", user.getEmail(), sentimentData);
                } catch (Exception e) {
                    log.info("Kafka Fallback!", e);
                    //emailService.sendMail(sentimentData.getEmail(), "Sentiment for past week!", sentimentData.getSentiment());
                }
            }

        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAllCache() {
        appCache.init();
    }

}
