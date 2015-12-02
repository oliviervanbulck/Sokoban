package view;

import model.Score;
import model.Sokoban;

import javax.swing.*;
import java.awt.*;

/**
 * Olivier Van Bulck
 * Date: 15/03/13
 */
public class HighscorePanel extends JFrame {

    public HighscorePanel(Sokoban game) {
        setTitle("Highscores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(d.width / 2 - 200, d.height / 2 - 200, 400, 400);
        Score[] high = game.getHighscores();
        String str = "";
        for (Score score : high) {
            if(score.getScore() == 0) {
                str += "Level "+score.getLevel()+": Nog geen highscore beschikbaar.\n";
            }
            else {
                str += "Level "+score.getLevel()+": "+score.getGebruiker()+" - "+score.getScore()+" stappen.\n";
            }
        }
        JTextArea display = new JTextArea();
        display.setText(str);
        display.setEditable(false);
        JScrollPane sp = new JScrollPane(display);

        add(sp, BorderLayout.CENTER);
        setVisible(true);
    }
}
