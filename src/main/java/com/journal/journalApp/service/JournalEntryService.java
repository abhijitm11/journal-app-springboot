package com.journal.journalApp.service;

import java.util.List;

import org.bson.types.ObjectId;
import com.journal.journalApp.entity.JournalEntry;

public interface JournalEntryService {

    JournalEntry saveEntry(JournalEntry entry, String user);

    List<JournalEntry> getAll();

    JournalEntry getJournalEntryById(ObjectId id);

    boolean deleteJournalEntryById(String username, ObjectId myId);

    JournalEntry saveEntry(JournalEntry oldEntry);
}
