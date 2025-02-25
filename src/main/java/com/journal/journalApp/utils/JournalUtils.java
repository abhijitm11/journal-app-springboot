package com.journal.journalApp.utils;

import org.springframework.stereotype.Component;

@Component
public class JournalUtils {

    public double fahrenheitToCelsius(double fahrenheit) {
        return Math.round((fahrenheit - 32) * 5 / 9 * 100.0) / 100.0;
    }
}
