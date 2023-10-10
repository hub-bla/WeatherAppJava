package weather;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

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


    public void displayWeather(Weather w){
        JLabel city = new JLabel();
        city.setText(w.getNameOfCity());
        city.setBounds(0, height/2, width, 10);
        city.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        add(city);
    }

}
