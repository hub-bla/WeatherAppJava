package weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

import static weather.Main.fetchData;

public class Coordinates {
    private double latitude;
    private double longitude;
    private String city;
    Coordinates(String city) {
        try {

            URL url = new URL ("https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1");
            HttpURLConnection con = fetchData(url);
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }
            scanner.close();

            con.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));
            JSONArray jsonArr = (JSONArray) jsonObj.get("results");
            JSONObject cityObj = (JSONObject) jsonArr.get(0);
            this.latitude = (double) cityObj.get("latitude");
            this.longitude = (double) cityObj.get("longitude");

        }catch(Exception e){
            throw new Error(e.getCause());
        }


    };

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
