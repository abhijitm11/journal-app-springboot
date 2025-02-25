package com.journal.journalApp.scheduler;

import com.journal.journalApp.cache.AppCache;
import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.entity.User;
import com.journal.journalApp.serviceImplementation.EmailServiceImpl;
import com.journal.journalApp.serviceImplementation.SentimentAnalysisServiceImpl;
import com.journal.journalApp.serviceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SentimentAnalysisServiceImpl sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailServiceImpl emailService;

    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUsersAndSendSentimentMail() {
        List<User> usersForSA = userService.findUsersForSA();
        for(User user: usersForSA) {
            List<JournalEntry> entries = user.getJournalEntries();
            // get the list of journal entries that were created in last week
            List<String> filteredEntries = entries.stream()
                    .filter(x-> x.getCreationDate().isBefore(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent).toList();

            String mergedEntry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(mergedEntry);
            emailService.sendMail(user.getEmail(), "Sentiment Analysis for last week!", sentiment);
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAllCache() {
        appCache.init();
    }



}
