package com.journal.journalApp.serviceImplementation;


import com.journal.journalApp.entity.User;
import com.journal.journalApp.repository.UserRepository;
import com.journal.journalApp.service.UserService;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(ObjectId id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public void deleteUserById(ObjectId myId) {
        userRepository.deleteById(myId);

    }

    @Override
    public User getUserByUsername(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}
