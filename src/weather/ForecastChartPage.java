package weather;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;

import static weather.Main.fetchData;
public class ForecastChartPage {
    private int width = 600;
    private int height = 450;
    FastScatterPlot plot;
    JFreeChart chart;
    JFrame frame;
    float[][] chartData;
    ForecastChartPage(Coordinates cor){
        try {

        chart = ChartFactory.createXYLineChart(
                "Weather Forecast",
                "Time",
                "Temperature",
                createDataset(cor),
                PlotOrientation.VERTICAL,
                false, true, false
                );

            ChartPanel chartPanel = new ChartPanel(chart);

            chartPanel.setPreferredSize(new java.awt.Dimension(450, 600));
            final XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
            renderer.setSeriesPaint( 0 , Color.BLUE );
            renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
            plot.setRenderer(renderer);
            frame = new JFrame();

            frame.setTitle("Weather forecast");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setSize(width, height);
            frame.add(chartPanel);
            frame.setVisible(true);
            frame.setContentPane(chartPanel);
        }catch (Exception e){
            System.out.println(e.getMessage());
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

    private XYDataset createDataset(Coordinates cor) throws Exception{
        try {
            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude="+Double.toString(cor.getLatitude())+"&longitude="+Double.toString(cor.getLongitude())+"&hourly=temperature_2m");
            JSONObject data = fetchData(url);
            JSONObject hourlyData = (JSONObject) data.get("hourly");
            JSONArray temperatureData = (JSONArray) hourlyData.get("temperature_2m");
            chartData = new float[temperatureData.toArray().length][2];

            final XYSeries weatherSeries = new XYSeries("Weather");
            for (int i = 0; i<chartData.length; i++){
                weatherSeries.add(i+10.0, (double) temperatureData.toArray()[i]);
//                chartData[i][1] =  ((Double) temperatureData.toArray()[i]);
            }

            final XYSeriesCollection dataset = new XYSeriesCollection(weatherSeries);
//            System.out.println();
            return dataset;
        }catch (Exception e){
            throw e;
        }
    }
}
