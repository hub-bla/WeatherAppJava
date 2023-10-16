package weather;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherPage implements ActionListener {
    private final int width = 450;
    private final int height = 600;
    private Weather w;
    private Coordinates cor;
    private  JLabel cityText = new JLabel();
    private  JLabel temperatureText = new JLabel();
    private final JLabel windSpeedText = new JLabel();
    private  JLabel weatherCondition = new JLabel();
    private  JButton openChartButton = new JButton();
    private  JLabel weatherConditionImg = new JLabel();
    private JLabel errorMessage = new JLabel();
    private ForecastChartPage chartPage = null;
    JTextField searchTextField = new JTextField();
    JFrame frame = new JFrame();
    WeatherPage(){
        displaySearch();
        frame.setTitle("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.add(cityText);
        frame.add(weatherCondition);
        frame.add(temperatureText);
        frame.add(weatherConditionImg);
        frame.add(openChartButton);
        frame.add(searchTextField);
        frame.add(windSpeedText);
        frame.add(errorMessage);
    }

    public void displaySearch() {
        int paddingToInc = 20;

        errorMessage.setBounds(0, (height/2)-100, width, 100);
        errorMessage.setFont( new Font(Font.MONOSPACED, Font.BOLD,16));
        errorMessage.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        errorMessage.setVisible(false);

        cityText.setBounds(0, (int) (height*0.1), width, 100);
        cityText.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        cityText.setFont( new Font(Font.MONOSPACED, Font.BOLD,60));

        temperatureText.setBounds(30, (int) (height - (height/2.5))+paddingToInc, 300, 50);
        temperatureText.setFont( new Font(Font.MONOSPACED, Font.BOLD,50));

        weatherConditionImg.setBounds(0,(int) (height*0.25), width, 200);
        weatherConditionImg.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        windSpeedText.setBounds(width-30-200, (int) (height - (height/3))+2*paddingToInc, 200, 50);
        windSpeedText.setHorizontalAlignment(SwingConstants.RIGHT);
        windSpeedText.setFont( new Font(Font.MONOSPACED, Font.BOLD,16));

        weatherCondition.setBounds(30, (int) (height - (height/3))+2*paddingToInc, 200, 50);
        weatherCondition.setFont( new Font(Font.MONOSPACED, Font.BOLD,16));


        openChartButton.setBounds((width/2)-100, (int)(height/1.2), 200, 25);
        openChartButton.addActionListener(this);
        openChartButton.setText("Open forecast chart");
        openChartButton.setHorizontalTextPosition(JButton.CENTER);
        openChartButton.setBorderPainted(false);
        openChartButton.setVisible(false);
        openChartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
                        onSpecifiedException(exception);

                    }finally {
                        searchTextField.setText(null);
                    }

                }
            }
        });


    }

    private ImageIcon pickImg(int conditionCode , int isDay){

        String imgPath = "./assets";
        if (isDay ==1){
            imgPath+="/day/";
        }else {
            imgPath+="/night/";
        }

        if (conditionCode == 0 || conditionCode == 1){imgPath+="Sun.png";}
        else if (conditionCode >=2 && conditionCode<=48){ imgPath+="Clouds.png";}
        else if (conditionCode >= 51 && conditionCode <=67) { imgPath+="Rain.png";}
        else if (conditionCode >= 71 && conditionCode <=77) { imgPath+="Snow.png";}
        else if (conditionCode >= 80 && conditionCode <=99) { imgPath+="Storm.png";}
        return new ImageIcon(imgPath);
    }



    private void fetchWeather(String city) throws SpecifiedException{

        cor = new Coordinates(city);
        w =  new Weather(cor);

        saveSearchToFile(cor.getCity());
        Color textColor= Color.BLACK;
        Color backgroundColor= Color.WHITE;
        Color buttonColor = new Color(17, 24, 39);

        if(w.getIsDay() ==0){
                textColor = Color.WHITE;
                backgroundColor = new Color(17, 24, 39);
                buttonColor = Color.WHITE;
        }
        errorMessage.setVisible(false);
        cityText.setForeground(textColor);
        temperatureText.setForeground(textColor);
        windSpeedText.setForeground(textColor);
        weatherCondition.setForeground(textColor);

        openChartButton.setBackground(buttonColor);
        openChartButton.setForeground(backgroundColor);
        openChartButton.setVisible(true);


        cityText.setText(String.format("%s",w.getNameOfCity()));

        temperatureText.setText(String.format("%s℃",w.getTemperatureInCelsius()));

        weatherConditionImg.setIcon(pickImg(w.getConditionCode(), w.getIsDay()));
        weatherConditionImg.setVisible(true);

        windSpeedText.setText(String.format("Wind: %s km/h", w.getWindSpeed()));
        weatherCondition.setText(String.format("%s", w.getWeatherCondition()));

        frame.getContentPane().setBackground(backgroundColor);
    }
    private void onSpecifiedException(SpecifiedException e) {
        cityText.setText(null);
        temperatureText.setText(null);
        windSpeedText.setText(null);
        weatherCondition.setText(null);
        weatherConditionImg .setIcon(null);
        openChartButton.setVisible(false);
        errorMessage.setText(e.getExceptionMessage());
        errorMessage.setVisible(true);
    }
    private void saveSearchToFile(String city) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("lastSearch.txt"));
            writer.write(city);
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


            }catch (SpecifiedException exception){
                onSpecifiedException(exception);
            }
            catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
