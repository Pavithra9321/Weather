package com.weather.view.utill;

import android.Manifest;

public class AppConstants {

    public static final String getWeatherReport = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    public static final String WeatherReportMode = "&mode=json&units=metric&cnt=3";

    public static final String COMMON_EXCEPTION = "Something went wrong!!!";

    public static final String WeatherAPIKey = "76032d7be8c2f7cd29c820e37207e15a";

    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
}
