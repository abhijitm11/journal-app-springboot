package com.journal.journalApp.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.journal.journalApp.entity.User;
import com.journal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.repository.JournalEntryRepository;
import com.journal.journalApp.service.JournalEntryService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(JournalEntryServiceImpl.class);

    @Override
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.getUserByUsername(username);
            journalEntry.setCreationDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            LOG.info("Saved Journal: {}", saved.getTitle());
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            return saved;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry!");
        }
    }

    @Override
    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    @Override
    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Override
    public JournalEntry getJournalEntryById(ObjectId id) {
        Optional<JournalEntry> entry = journalEntryRepository.findById(id);
        return entry.orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteJournalEntryById(String username, ObjectId journalId) {
        boolean deleted = false;
        try {
            User user = userService.getUserByUsername(username);
            Optional<JournalEntry> entry = journalEntryRepository.findById(journalId);
            deleted = user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));
            if(deleted) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(journalId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

}
