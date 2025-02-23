package com.journal.journalApp.controller;

import com.journal.journalApp.entity.User;
import com.journal.journalApp.repository.UserRepository;
import com.journal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userRepository.findAll();
        if(!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        User admin = userService.saveAdmin(user);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

}
