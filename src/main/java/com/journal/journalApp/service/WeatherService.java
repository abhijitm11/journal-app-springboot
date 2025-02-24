package com.journal.journalApp.service;

import com.journal.journalApp.response.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeather(String city);
}
