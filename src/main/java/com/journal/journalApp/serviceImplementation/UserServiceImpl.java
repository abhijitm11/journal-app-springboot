package com.journal.journalApp.serviceImplementation;


import com.journal.journalApp.entity.User;
import com.journal.journalApp.repository.UserRepository;
import com.journal.journalApp.service.UserService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;


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

    @Override
    public User saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        return userRepository.save(user);
    }

    /*@Override
    public List<User> findUsersForSA() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        List<User> users =  userRepository.findBySentimentAnalysisTrueAndEmailRegex(emailRegex);
        return users;
    }*/

    @Override
    public List<User> findUsersForSA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        return mongoTemplate.find(query, User.class);
    }

}
