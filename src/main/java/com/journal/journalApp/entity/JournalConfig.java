package com.journal.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection="journalConfigs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalConfig {
    @NonNull
    private String key;
    @NonNull
    private String value;

}
