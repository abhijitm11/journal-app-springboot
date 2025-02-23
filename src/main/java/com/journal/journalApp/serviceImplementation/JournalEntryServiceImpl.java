package com.journal.journalApp.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.journal.journalApp.entity.User;
import com.journal.journalApp.service.UserService;
import org.bson.types.ObjectId;
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

    @Override
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.getUserByUsername(username);
            journalEntry.setCreationDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);

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
    public void deleteJournalEntryById(String username, ObjectId journalId) {
        try {
            Optional<User> user = Optional.ofNullable(userService.getUserByUsername(username));
            Optional<JournalEntry> entry = journalEntryRepository.findById(journalId);
            if(user.isEmpty() || entry.isEmpty()) {
                throw new IllegalArgumentException();
            }

            user.get().getJournalEntries().removeIf(x -> x.getId().equals(journalId));
            userService.saveUser(user.get());

            journalEntryRepository.deleteById(journalId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
