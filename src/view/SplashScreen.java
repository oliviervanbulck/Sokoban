package view;

import model.Muziek;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * Author: Olivier Van Bulck
 * Date: 3/03/11
 */
public class SplashScreen extends JWindow {
    public SplashScreen() {
        Muziek startup = new Muziek("sound/startup.wav");
        startup.start();
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("../images/splash.png")));
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        //centreren:
        Dimension d = getToolkit().getScreenSize();
        int x = (int) d.getWidth() / 2 - 150;
        int y = (int) d.getHeight() / 2 - 150;
        setBounds(x, y, 300, 300);
        pack();
        setVisible(true);
        try {
            Thread.sleep(6000);  // 6 sec pauzeren
        } catch (InterruptedException e) {
            // doe niets
        }
        this.dispose();
    }
}