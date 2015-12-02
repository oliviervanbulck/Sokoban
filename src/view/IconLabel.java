package view;

import javax.swing.*;

/**
 * Olivier Van Bulck
 * Date: 13/03/13
 */
public class IconLabel extends JLabel {
    private int number;

    public IconLabel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
