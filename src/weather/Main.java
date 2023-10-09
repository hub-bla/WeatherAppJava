package weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)  {

        System.out.println("Hello World");

        Weather wthr = new Weather("Berlin");

//        System.out.println(corr.getLatitude());
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