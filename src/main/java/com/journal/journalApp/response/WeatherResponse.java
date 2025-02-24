package com.journal.journalApp.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class WeatherResponse {
    private Main main;

    @Data
    public class Main{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }
}

