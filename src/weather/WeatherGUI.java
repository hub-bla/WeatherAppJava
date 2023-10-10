package weather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WeatherGUI extends JFrame {
    private int width = 450;
    private int height = 600;

    public WeatherGUI(){
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(width, height);
        //app opens at center
        setLocationRelativeTo(null);
        // to manually position components
        setLayout(null);
        //disable resizing
        setResizable(false);
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

        JLabel loadingText = new JLabel();
        loadingText.setBounds(0, height/2, width, 25);
        loadingText.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        loadingText.setVisible(false);
        loadingText.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println(e);
                loadingText.setText("Loading...");
            }


        });
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    loadingText.setVisible(true);
                    try {
                        Coordinates cor = new Coordinates(searchTextField.getText());

                        Weather w =  new Weather(cor);
                        System.out.println(w.getWindSpeed());
                        cityText.setText(String.format("City: %s",w.getNameOfCity()));

                        temperatureText.setText(String.format("Temperature: %s ℃",w.getTemperatureInCelsius()));

                        windSpeedText.setText(String.format("Wind speed: %s km/h", w.getWindSpeed()));
                    }catch (SpecifiedException exception){
                        cityText.setText(exception.getExceptionMessage());
                    }finally {
                        searchTextField.setText(null);
                    }

                }
            }
        });

        add(loadingText);
        add(cityText);
        add(temperatureText);
        add(windSpeedText);
        add(searchTextField);
    }


}
