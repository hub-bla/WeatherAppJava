package weather;

import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherPage implements ActionListener {
    private int width = 450;
    private int height = 600;

    private Weather w;
    private Coordinates cor;
    private JLabel cityText = new JLabel();
    private JLabel temperatureText = new JLabel();
    private JLabel windSpeedText = new JLabel();
    private JLabel weatherCondition = new JLabel();
    private JButton openChartButton = new JButton();
    private ForecastChartPage chartPage = null;
    JTextField searchTextField = new JTextField();
    JFrame frame = new JFrame();
    WeatherPage(){
        displaySearch();
        frame.setTitle("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(width, height);
        //app opens at center
        frame.setLocationRelativeTo(null);
        // to manually position components
        frame.setLayout(null);
        //disable resizing
        frame.setResizable(false);
        frame.add(cityText);
        frame.add(weatherCondition);
        frame.add(temperatureText);
        frame.add(openChartButton);
        frame.add(searchTextField);
        frame.add(windSpeedText);
    }

    public void displaySearch() {
        int paddingToInc = 20;

        cityText.setBounds(0, height/2, width, 25);
        cityText.setHorizontalAlignment(SwingConstants.HORIZONTAL);


        temperatureText.setBounds(0, (height/2)+paddingToInc, width, 25);
        temperatureText.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        windSpeedText.setBounds(0, (height/2)+paddingToInc*2, width, 25);
        windSpeedText.setHorizontalAlignment(SwingConstants.HORIZONTAL);


        weatherCondition.setBounds(0, (height/2)+paddingToInc*3, width, 25);
        weatherCondition.setHorizontalAlignment(SwingConstants.HORIZONTAL);


        openChartButton.setBounds(0, (height/2)+paddingToInc*4, width, 25);
        openChartButton.addActionListener(this);
        openChartButton.setText("Open forecast chart");
        openChartButton.setHorizontalTextPosition(JButton.CENTER);
        openChartButton.setVisible(false);

        searchTextField.setBounds(15, 15, (int) (width*0.9), 25);




        String previousSearch = getCityFromTextFile();

        if (previousSearch != null){
            try{
                fetchWeather(previousSearch);

            }catch (SpecifiedException e){
                System.out.println(e.getExceptionMessage());
            }
        }

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        closeChartIfOpened();
                        String city = new String(searchTextField.getText().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                        fetchWeather(city);
                        System.out.println(searchTextField.getText());


                    }catch (SpecifiedException exception){
                        cityText.setText(exception.getExceptionMessage());

                    }finally {
                        searchTextField.setText(null);
                    }

                }
            }
        });


    }

    private void fetchWeather(String city) throws SpecifiedException{
        System.out.println(city);
        cor = new Coordinates(city);

        w =  new Weather(cor);
        saveSearchToFile(cor.getCity());
        cityText.setText(String.format("City: %s",w.getNameOfCity()));

        temperatureText.setText(String.format("Temperature: %s ℃",w.getTemperatureInCelsius()));

        windSpeedText.setText(String.format("Wind speed: %s km/h", w.getWindSpeed()));
        weatherCondition.setText(String.format("Weather condition: %s", w.getWeatherCondition()));
        openChartButton.setVisible(true);
    }

    private void saveSearchToFile(String city) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("lastSearch.txt"));
            writer.write(city);

            // Close the writer to save the changes
            writer.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private String getCityFromTextFile(){
        try {
            File file = new File("lastSearch.txt");
            if (file.createNewFile()){
                return null;
            }
            Scanner scanner = new Scanner(file);
            if(!scanner.hasNextLine()){
                return null;
            }
            String city = scanner.nextLine();
            scanner.close();
            return city;
        }catch (Exception e){
            return null;
        }


    };

    private void closeChartIfOpened(){
        if (chartPage != null) {
            chartPage.getFrame().dispose();
            chartPage =null;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openChartButton) {

            try {
                closeChartIfOpened();
                chartPage = new ForecastChartPage(cor);


            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
