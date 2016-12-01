package com.assistant.service;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class WeatherService {
    public String getCurrentWeather(){
        ForecastIO fio = new ForecastIO("c1e05a3b37d110e7c0c31616c782f7c4");
        fio.setUnits(ForecastIO.UNITS_SI);
        fio.setExcludeURL("hourly,minutely");
        fio.getForecast("10.801272", "106.660448");
        FIOCurrently currently = new FIOCurrently(fio);
        return currently.get().summary().toLowerCase() + " and temperature " + currently.get().apparentTemperature();
    }

    public String getCurrentTemperature(){
        ForecastIO fio = new ForecastIO("c1e05a3b37d110e7c0c31616c782f7c4");
        fio.setUnits(ForecastIO.UNITS_SI);
        fio.setExcludeURL("hourly,minutely");
        fio.getForecast("10.801272", "106.660448");
        FIOCurrently currently = new FIOCurrently(fio);
        return "" + currently.get().apparentTemperature();
    }
}
