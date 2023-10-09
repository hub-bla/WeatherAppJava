package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class Main {
    public static void main(String[] args)  {

        System.out.println("Hello World");

        Coordinates corr = new Coordinates("Berlin");

        System.out.println(corr.getLatitude());
    }


    public static HttpURLConnection fetchData(URL url){
        try {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.connect();

        return con;
        }catch (IOException e) {
            throw new Error("Couldn't get data", e.getCause());
        }


    }
}