package weather;

import org.json.simple.JSONObject;

import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

import static weather.Main.fetchData;

public class Weather {

    private double temperatureInCelsius;

    private double windSpeed;
    private String nameOfCity;
    private String weatherCondition;
    Weather(Coordinates cor) throws SpecifiedException{

        try {
            WeatherConditons weatherConditions = new WeatherConditons();
            HashMap<String, String> weatherCodesMap = weatherConditions.getWeatherConditions();
            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=" + cor.getLatitude() + "&longitude=" + cor.getLongitude() + "&current_weather=true");
            JSONObject data = fetchData(url);
            JSONObject weatherData = (JSONObject) data.get("current_weather");
            weatherCondition = weatherCodesMap.get(weatherData.get("weathercode").toString());
            windSpeed =  Double.parseDouble(String.valueOf(weatherData.get("windspeed"))) ;
            nameOfCity = cor.getCity();
            temperatureInCelsius = Double.parseDouble(String.valueOf(weatherData.get("temperature")));
        }catch (SpecifiedException e){
            throw e;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public double getTemperatureInCelsius() {
        return temperatureInCelsius;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getNameOfCity() {
        return nameOfCity;
    }
}
