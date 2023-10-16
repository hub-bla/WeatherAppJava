package weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)  {
       new WeatherPage();
        

    }


    public static JSONObject readData(HttpURLConnection con){
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(String.valueOf(resultJson));
        }
        catch (Exception e) {
            return null;
        }

    }
    public static JSONObject fetchData(URL url) throws SpecifiedException{
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return readData(con);
        }
        catch (IOException e) {
             throw new NoConnection();
        }
    }






}