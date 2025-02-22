package com.journal.journalApp.serviceImplementation;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.repository.JournalEntryRepository;
import com.journal.journalApp.service.JournalEntryService;

@Service
public class JournalEntryServiceImplementation implements JournalEntryService{

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Override
    public JournalEntry saveEntry(JournalEntry entry) {
        return journalEntryRepository.save(entry);
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
    public void deleteJournalEntryById(ObjectId myId) {
        journalEntryRepository.deleteById(myId);
    }

}
