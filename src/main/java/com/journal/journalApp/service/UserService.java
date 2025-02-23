package com.journal.journalApp.service;

import com.journal.journalApp.entity.User;
import lombok.NonNull;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    User saveNewUser(User user);

    void saveUser(User user);

    List<User> getAll();

    User getUserById(ObjectId id);

    void deleteUserById(ObjectId myId);

    User getUserByUsername(@NonNull String username);

    void deleteUser(String username);

    User saveAdmin(User user);
}
