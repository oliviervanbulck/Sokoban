package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Olivier Van Bulck
 * Date: 14/11/12
 */
public class Sokoban {
    private Muziek muziek = new Muziek("sound/bg.wav");
    private Speelveld speelveld;

    public Muziek getBackgroundMusic() {
        return muziek;
    }

    public String getUitleg() {
        String input = "";
        try {
            Scanner scanner = new Scanner(new FileReader("database/regels.inf"));
            while(scanner.hasNextLine()) {
                input += scanner.nextLine() + "\n";
            }
        }catch(IOException IOE){}

        return input;
    }

    public boolean login(String username, String password) throws Exception {
        GebruikersIO.login(username, password);
        if(GebruikersIO.isLoggedIn()) {
            HighscoreIO.laadScores();
        }
        return GebruikersIO.isLoggedIn();
    }
    public void logout() throws Exception {
        HighscoreIO.saveScores();
        GebruikersIO.logout();
    }

    public boolean checkLogin() {
        return GebruikersIO.isLoggedIn();
    }

    public boolean createUser(String username, String password) {
        GebruikersIO.createUser(username, password);
        return GebruikersIO.isLoggedIn();
    }

    public Speler getLoggedInUser() {
        return GebruikersIO.getLoggedInUser();
    }

    public boolean startSpel() {
        return startSpel(getLoggedInUser().getLevel());
    }

    public boolean startSpel(int level) {
        if(GebruikersIO.isLoggedIn()) {
            this.speelveld = new Speelveld(new Level("database/levels.txt", level));
            return true;
        }

        return false;
    }

    public Speelveld getSpeelveld() {
        return this.speelveld;
    }

    public void moveLeft() {
        speelveld.moveLeft();
    }

    public void moveRight() {
        speelveld.moveRight();
    }

    public void moveUp() {
        speelveld.moveUp();
    }

    public void moveDown() {
        speelveld.moveDown();
    }

    public boolean isGewonnen(boolean c) {
        return speelveld.isGewonnen(c);
    }

    public int getAantalLevels() {
        boolean change = false;
        if(speelveld == null) {this.speelveld = new Speelveld(new Level("database/levels.txt", 1));change = true;}
        int levels = speelveld.getAantalLevels();
        if(change == true) this.speelveld = null;
        return levels;
    }

    public Score[] getHighscores() {
        return HighscoreIO.getHighscores();
    }
}