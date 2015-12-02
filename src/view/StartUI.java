package view;

import model.Score;
import model.Sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: olivier
 * Date: 23-2-13
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class StartUI extends JFrame {
    private JButton login = new JButton("Login");
    private JButton newUser = new JButton("Nieuwe gebruiker");
    private JButton highscores = new JButton("Highscores");
    private JButton spelregels = new JButton("Spelregels");
    private JButton muziek = new JButton("Start/stop muziek");
    private JButton einde = new JButton("Einde");
    private Sokoban game = new Sokoban();

    public StartUI() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(d.width / 2 - 100, d.height / 2 - 200, 200, 400);
        setTitle("Sokoban - Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        add(login);
        add(newUser);
        add(highscores);
        add(spelregels);
        add(muziek);
        add(einde);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(StartUI.this, "Gelieve je gebruikersnaam in te voeren:", "Inloggen", JOptionPane.QUESTION_MESSAGE);
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Gelieve je wachtwoord in te voeren:");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(StartUI.this, panel, "Inloggen",
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                String password = null;
                if (option == 0) // pressing OK button
                {
                    password = new String(pass.getPassword());
                }
                try {
                    game.login(username, password);
                    if (game.checkLogin() == true) {
                        setVisible(false);
                        LoggedInUI ui = new LoggedInUI(game, StartUI.this);
                    } else {
                        JOptionPane.showMessageDialog(StartUI.this, "Deze combinatie van gebruikersnaam en wachtwoord is niet correct.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(StartUI.this, "Er is een fout opgetreden bij het lezen van een bestand.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(StartUI.this, "Welke gebruikersnaam wil je?", "Gebruiker aanmaken", JOptionPane.QUESTION_MESSAGE);
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Welk wachtwoord wil je?");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(StartUI.this, panel, "Gebruiker aanmaken",
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);
                String password = null;
                if (option == 0) // pressing OK button
                {
                    password = new String(pass.getPassword());
                }
                try {
                    game.createUser(username, password);
                    if (game.checkLogin() == true) {
                        setVisible(false);
                        LoggedInUI ui = new LoggedInUI(game, StartUI.this);
                    } else {
                        JOptionPane.showMessageDialog(StartUI.this, "Er is een fout opgetreden bij het aanmaken van je account.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(StartUI.this, "Er is een fout opgetreden bij het lezen van een bestand.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        highscores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HighscorePanel h = new HighscorePanel(StartUI.this.game);
            }
        });
        spelregels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StartUI.this, StartUI.this.game.getUitleg(), "Spelregels", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        muziek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getBackgroundMusic().hasError() == false) {
                    if(game.getBackgroundMusic().isMuziekSpelend() == false) {
                        game.getBackgroundMusic().start();
                    }
                    else {
                        game.getBackgroundMusic().stop();
                    }
                }
            }
        });
        einde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
}