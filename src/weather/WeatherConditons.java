package weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class WeatherConditons{

    HashMap<String, String> weatherConditions;

    WeatherConditons() throws Exception {
        weatherConditions = new HashMap<String, String>();
        try{

        File jsonWeatherCodes = new File("weatherCodes.json");
        Scanner scanner = new Scanner(jsonWeatherCodes);
        StringBuilder jsonFile = new StringBuilder();
        while(scanner.hasNextLine()){
            jsonFile.append(scanner.nextLine());
        }
        JSONParser parser = new JSONParser();

        JSONObject jsonData = (JSONObject) parser.parse(jsonFile.toString());

        JSONArray weatherCodesJSONArr = (JSONArray) jsonData.get("codes");
        Object[] weatherCodes =  weatherCodesJSONArr.toArray();
        JSONArray codesMeaningJSONArr = (JSONArray) jsonData.get("meaning");
        Object[] codesMeaning =  codesMeaningJSONArr.toArray();
        for (int i=0; i<weatherCodes.length; i++){
            weatherConditions.put(weatherCodes[i].toString(), codesMeaning[i].toString());
        }

        scanner.close();

        }catch (Exception e){
            throw e;
        }

    }

    public HashMap<String, String> getWeatherConditions() {
        return weatherConditions;
    }
}
