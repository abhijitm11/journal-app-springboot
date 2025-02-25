package com.journal.journalApp.repository;

import com.journal.journalApp.entity.JournalConfig;
import com.journal.journalApp.entity.User;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalConfigRepository extends MongoRepository<JournalConfig, ObjectId>{

}
