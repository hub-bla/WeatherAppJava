package weather;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static weather.Main.fetchData;
public class ForecastChartPage {
    JFreeChart chart;
    JFrame frame;
    ForecastChartPage(Coordinates cor) throws SpecifiedException{
        try {
            int width = 600;
            int height = 450;
            chart = ChartFactory.createTimeSeriesChart(
                    "Weather in " + cor.getCity(),
                    "Time",
                    "Temperature",
                    createDataset(cor),
                    false, true, false
                    );

            ChartPanel chartPanel = new ChartPanel(chart);

            chartPanel.setPreferredSize(new java.awt.Dimension(450, 600));

            XYPlot plot = chart.getXYPlot();

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
            renderer.setSeriesPaint( 0 , Color.BLUE );
            renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
            plot.setRenderer(renderer);


            frame = new JFrame();
            frame.setTitle("Weather forecast");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null);
            frame.setSize(width, height);
            frame.add(chartPanel);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(chartPanel);

        }catch (SpecifiedException e){
            throw e;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private XYDataset createDataset(Coordinates cor) throws SpecifiedException {
        try {

            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=" + cor.getLatitude() + "&longitude=" + cor.getLongitude() + "&daily=temperature_2m_max&timezone=Europe%2FBerlin");
            JSONObject data = fetchData(url);



            JSONObject dailyData = (JSONObject) data.get("daily");
            JSONArray temperatureDataJSONArr = (JSONArray) dailyData.get("temperature_2m_max");
            Object[] temperatureData = temperatureDataJSONArr.toArray();

            JSONArray daysDataJSONArr = (JSONArray) dailyData.get("time");
            Object[] daysData = daysDataJSONArr.toArray();

            TimeSeries weatherSeries = new TimeSeries("Weather");

            for (int i = 0; i < temperatureData.length; i++) {
                String dateStr = daysData[i].toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date day = sdf.parse(dateStr);
                weatherSeries.add(new Day(day), (double) temperatureData[i]);
            }

            return new TimeSeriesCollection(weatherSeries);
        } catch (SpecifiedException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public JFrame getFrame() {
        return frame;
    }
}
