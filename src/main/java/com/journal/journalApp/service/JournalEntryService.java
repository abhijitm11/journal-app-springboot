package com.journal.journalApp.service;

import java.util.List;
import org.bson.types.ObjectId;
import com.journal.journalApp.entity.JournalEntry;
import org.springframework.http.ResponseEntity;

public interface JournalEntryService {

    JournalEntry saveEntry(JournalEntry entry);

    List<JournalEntry> getAll();

    JournalEntry getJournalEntryById(ObjectId id);

    void deleteJournalEntryById(ObjectId myId);

}
