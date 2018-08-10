package com.weather.view.mvp_view;

import com.weather.model.WeatherModel;

import java.util.ArrayList;

/**
 * Created by cds123 on 25-11-2017.
 */

public interface WeatherMVP {
    interface WeatherView{
        void showSuccess(String success, ArrayList<WeatherModel> list);
        void showError(String success, String message);
    }
    interface Presenter{
        void getWeatherReport(String city);
    }
}
