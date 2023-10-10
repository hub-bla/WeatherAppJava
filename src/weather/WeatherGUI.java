package weather;

import javax.swing.*;
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
        JLabel city = new JLabel();
        city.setBounds(0, height/2, width, 20);
        city.setHorizontalAlignment(SwingConstants.HORIZONTAL);


        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, (int) (width*0.9), 25);
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    Weather w =  new Weather(searchTextField.getText());
                    city.setText(w.getNameOfCity());
                }
            }
        });

        add(city);
        add(searchTextField);
    }

//    public void displayWeather(Weather w){
//
//
//        city.setText(w.getNameOfCity());
//        city.setBounds(0, height/2, width, 10);
//        city.setHorizontalAlignment(SwingConstants.HORIZONTAL);
//        add(city);
//        System.out.println(w.getNameOfCity());
//
//    }

}
