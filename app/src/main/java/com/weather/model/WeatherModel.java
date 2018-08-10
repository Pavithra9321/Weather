package com.weather.model;

public class WeatherModel {

    public String getMrgTemp() {
        return mrgTemp;
    }

    public void setMrgTemp(String mrgTemp) {
        this.mrgTemp = mrgTemp;
    }

    public String getNightTemp() {
        return NightTemp;
    }

    public void setNightTemp(String nightTemp) {
        NightTemp = nightTemp;
    }

    public String getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp) {
        this.dayTemp = dayTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    String mrgTemp;
    String NightTemp;
    String dayTemp;
    String humidity;
    String pressure;
    String description;
    String cloud;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;
}
