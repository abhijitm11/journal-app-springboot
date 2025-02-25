package com.journal.journalApp.serviceImplementation;

import com.journal.journalApp.cache.AppCache;
import com.journal.journalApp.constants.Keys;
import com.journal.journalApp.response.WeatherResponse;
import com.journal.journalApp.service.WeatherService;
import com.journal.journalApp.utils.JournalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private AppCache appCache;

    private static final String API = "https://open-weather13.p.rapidapi.com/city/<city>/EN";
    private static final String HOST = "open-weather13.p.rapidapi.com";

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final RestTemplate restTemplate;
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherResponse getWeather(String city) {
        // Define the URL for the API endpoint
        String finalUrl = API.replace("<city>", city);

        // Set up headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", appCache.getAppCache().get(Keys.WEATHER_API.toString()));
        headers.set("x-rapidapi-host", HOST);

        // Create an entity with the headers (RestTemplate will use this to make the request)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the GET request and get the response as a String
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, WeatherResponse.class);

        // Return the response body (weather data in this case)
        return response.getBody();

    }
}
