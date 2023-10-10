package weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

import static weather.Main.fetchData;

public class Coordinates  {
    private double latitude;
    private double longitude;
    private String city;

    private String errorMessage = null;
    Coordinates(String city) throws SpecifiedException {
        try {

            URL url = new URL ("https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1");

            JSONObject data = fetchData(url);
            System.out.println(data);
            if (!data.containsKey("results")){
                throw new NotFound();
            }

            JSONArray jsonArr = (JSONArray) data.get("results");
            JSONObject cityObj = (JSONObject) jsonArr.get(0);
            System.out.println(cityObj.get("name"));
            this.latitude = (double) cityObj.get("latitude");
            this.longitude = (double) cityObj.get("longitude");
            this.city = (String) cityObj.get("name");

        }catch (SpecifiedException e){
            throw e;
        }
        catch (MalformedURLException e){
            System.out.println(e.getMessage());
        }


    };

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
