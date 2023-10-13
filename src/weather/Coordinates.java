package weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static java.net.URLEncoder.encode;
import java.net.*;

import static weather.Main.fetchData;

public class Coordinates  {
    private double latitude;
    private double longitude;
    private String city;
    private String country;
    Coordinates(String city) throws SpecifiedException {
        try {

            URL url = new URL ("https://geocoding-api.open-meteo.com/v1/search?name=" + encode((city.replace(" ", "%20")),"UTF-8") + "&count=1");

            JSONObject data = fetchData(url);
            if (!data.containsKey("results")){
                throw new NotFound(city);
            }

            JSONArray jsonArr = (JSONArray) data.get("results");
            JSONObject cityObj = (JSONObject) jsonArr.get(0);
            this.country = cityObj.get("country").toString();
            this.latitude = (double) cityObj.get("latitude");
            this.longitude = (double) cityObj.get("longitude");
            this.city = (String) cityObj.get("name");

        }catch (SpecifiedException e){
            throw e;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    };

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


}
