package view;

import model.Sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Olivier Van Bulck
 * Date: 28/02/13
 */
public class LoggedInUI extends JFrame {
    private JButton verdergaan = new JButton("Verdergaan met volgende level");
    private JButton kiesLevel = new JButton("Level kiezen");
    private JButton spelregels = new JButton("Spelregels");
    private JButton highscores = new JButton("Highscores");
    private JButton uitloggen = new JButton("Uitloggen");
    private Sokoban game;
    private JFrame previous;

    public LoggedInUI(Sokoban game, JFrame previous) {
        this.previous = previous;
        this.game = game;
        setNaamButton();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(d.width / 2 - 100, d.height / 2 - 200, 200, 400);
        setTitle("Sokoban - " + this.game.getLoggedInUser().getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        add(verdergaan);
        add(kiesLevel);
        add(spelregels);
        add(highscores);
        add(uitloggen);
        verdergaan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SpelUI ui = new SpelUI(LoggedInUI.this.game, LoggedInUI.this, LoggedInUI.this.game.getLoggedInUser().getLevel());
                IconChooser ic = new IconChooser(LoggedInUI.this, LoggedInUI.this.game);
                LoggedInUI.this.dispose();
            }
        });
        kiesLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String getal = JOptionPane.showInputDialog(LoggedInUI.this, "Geef het level in dat je wil spelen (1-"+LoggedInUI.this.game.getAantalLevels()+"):");
                try {
                    int get = Integer.parseInt(getal);
                    if(get > 0 && get <= LoggedInUI.this.game.getAantalLevels()) {
                        new IconChooser(LoggedInUI.this, LoggedInUI.this.game, get);
                        LoggedInUI.this.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(LoggedInUI.this, "Gelieve een waarde in te vullen tussen 1 en 50.");
                    }
                } catch(NumberFormatException er) {
                    JOptionPane.showMessageDialog(LoggedInUI.this, "Gelieve een getal in te voeren!");
                }
            }
        });
        spelregels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoggedInUI.this, LoggedInUI.this.game.getUitleg(), "Spelregels", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        highscores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighscorePanel(LoggedInUI.this.game);
            }
        });
        uitloggen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoggedInUI.this.game.logout();
                } catch (Exception e1) {
                    //Doe niets
                }
                LoggedInUI.this.dispose();
                LoggedInUI.this.previous.setVisible(true);
            }
        });
        this.setVisible(true);
    }

    private void setNaamButton() {
        verdergaan.setText("Verdergaan met level: "+this.game.getLoggedInUser().getLevel());
    }

    @Override
    public void revalidate() {
        super.revalidate();
        setNaamButton();
    }
}
