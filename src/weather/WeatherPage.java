package weather;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WeatherPage implements ActionListener {
    private int width = 450;
    private int height = 600;

    private Weather w;
    private Coordinates cor;
    JFrame frame = new JFrame();
    JButton openChartButton;
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
    }

    public void displaySearch() {
        int paddingToInc = 20;

        JLabel cityText = new JLabel();
        cityText.setBounds(0, height/2, width, 25);
        cityText.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        JLabel temperatureText = new JLabel();
        temperatureText.setBounds(0, (height/2)+paddingToInc, width, 25);
        temperatureText.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        JLabel windSpeedText = new JLabel();
        windSpeedText.setBounds(0, (height/2)+paddingToInc*2, width, 25);
        windSpeedText.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, (int) (width*0.9), 25);

        openChartButton = new JButton();
        openChartButton.setBounds(0, (height/2)+paddingToInc*3, width, 25);
        openChartButton.addActionListener(this);
        openChartButton.setText("Open forecast chart");
        openChartButton.setHorizontalTextPosition(JButton.CENTER);
        openChartButton.setVisible(false);
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        cor = new Coordinates(searchTextField.getText());

                        w =  new Weather(cor);
                        System.out.println(w.getWindSpeed());
                        cityText.setText(String.format("City: %s",w.getNameOfCity()));

                        temperatureText.setText(String.format("Temperature: %s ℃",w.getTemperatureInCelsius()));

                        windSpeedText.setText(String.format("Wind speed: %s km/h", w.getWindSpeed()));
                        openChartButton.setVisible(true);

                    }catch (SpecifiedException exception){
                        cityText.setText(exception.getExceptionMessage());
                    }finally {
                        searchTextField.setText(null);
                    }

                }
            }
        });



        frame.add(cityText);
        frame.add(temperatureText);
        frame.add(windSpeedText);
        frame.add(searchTextField);
        frame.add(openChartButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openChartButton) {
            try {

                ForecastChartPage chartPage = new ForecastChartPage(cor);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
