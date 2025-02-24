package com.journal.journalApp.controller;

import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.entity.User;
import com.journal.journalApp.response.WeatherResponse;
import com.journal.journalApp.service.UserService;
import com.journal.journalApp.service.WeatherService;
import com.journal.journalApp.utils.JournalUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private JournalUtils journalUtils;

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.getUserByUsername(username);

        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(userInDb);

        return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");

        String greetings = "";
        if(weatherResponse != null) {
            double currentWeather = journalUtils.fahrenheitToCelsius(weatherResponse.getMain().feels_like);
            greetings = ", Weather feels like "+currentWeather;
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+greetings, HttpStatus.OK);
    }

}
