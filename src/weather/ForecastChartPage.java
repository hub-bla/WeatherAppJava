package weather;

import javax.swing.*;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.FastScatterPlot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;

import static weather.Main.fetchData;
public class ForecastChartPage {
    private int width = 450;
    private int height = 600;
    FastScatterPlot plot;
    JFreeChart chart;
    ChartFrame frame;
    float[][] chartData;
    ForecastChartPage(Coordinates cor) throws Exception{
        try {
            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude="+Double.toString(cor.getLatitude())+"&longitude="+Double.toString(cor.getLongitude())+"&hourly=temperature_2m");
            JSONObject data = fetchData(url);
            JSONObject hourlyData = (JSONObject) data.get("hourly");
            JSONArray temperatureData = (JSONArray) hourlyData.get("temperature_2m");
//            temperatureData.toArray();
            Object[] tempArr = temperatureData.toArray();
            chartData = new float[temperatureData.toArray().length][2];
            System.out.println(temperatureData.toArray()[0].getClass().getName());
            for (int i = 0; i<chartData.length; i++){
                chartData[i][0] = i+10;
                chartData[i][1] =  ((Double) temperatureData.toArray()[i]).floatValue();

            }
//            plot = new FastScatterPlot();
//            plot.setData(chartData);
//            chart = new JFreeChart();
        }catch (Exception e){
            throw e;
        }
//        frame.setTitle("Chart");
//        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//
//        frame.setSize(width, height);
//        //app opens at center
//        frame.setLocationRelativeTo(null);
//        // to manually position components
//        frame.setLayout(null);
//        //disable resizing
//        frame.setResizable(false);
//        frame.setVisible(true);


//        plot.setData();
//        chart = new JFreeChart();
    }
}
