package weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import javax.swing.*;
import java.net.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WeatherGUI wGUI = new WeatherGUI();
                wGUI.setVisible(true);
                wGUI.displaySearch();
            }
        });
//        System.out.println("Hello World");
//
//        Weather wthr = new Weather("Berlin");
//
//        System.out.println(wthr.getTemperatureInCelsius());
    }


    public static JSONObject fetchData(URL url){
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());

            while (scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(String.valueOf(resultJson));
        }catch (Exception e) {
            throw new Error("Couldn't get data", e.getCause());
        }
    }




}