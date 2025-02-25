package com.journal.journalApp.cache;

import com.journal.journalApp.entity.JournalConfig;
import com.journal.journalApp.repository.JournalConfigRepository;
import com.journal.journalApp.serviceImplementation.WeatherServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
@NoArgsConstructor
public class AppCache {

    @Autowired
    private JournalConfigRepository journalConfigRepository;

    private Map<String, String> appCache;

    private static final Logger LOG = LoggerFactory.getLogger(AppCache.class);

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<JournalConfig> all = journalConfigRepository.findAll();
        for(JournalConfig journalConfig: all) {
            appCache.put(journalConfig.getKey(), journalConfig.getValue());
        }
    }
}
