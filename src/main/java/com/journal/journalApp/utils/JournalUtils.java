package com.journal.journalApp.utils;

import org.springframework.stereotype.Component;

@Component
public class JournalUtils {

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

}
