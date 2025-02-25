package com.journal.journalApp.service;

import com.journal.journalApp.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceImplTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "phil",
            "claire",
            "ross",
            "rachel"
    })
    public void testGetUserByUsername(String name) {
        assertNotNull(userRepository.findByUsername(name), "failed for: "+name);
    }

}
