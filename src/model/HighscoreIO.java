package model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Olivier Van Bulck
 * Date: 21/12/12
 */
public class HighscoreIO {
    private static final String path = "database/scores.enc";
    private static Scanner scanner;
    private static ArrayList<Score> scoreList = new ArrayList<Score>();
    private static Levels l = new Levels("database/levels.txt");

    public static void laadScores() throws Exception {
        scanner = new Scanner(new FileReader(path));
        String input = "";
        while(scanner.hasNextLine()) {
            input += scanner.nextLine();
        }
        scanner = new Scanner(Encrypt.decrypt(input));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.length() != 0) {
                String[] items = line.split("\\|");
                if(items.length == 3) {
                    scoreList.add(new Score(items[0], Integer.parseInt(items[1]), Integer.parseInt(items[2])));
                }
            }
        }
    }

    public static Score[] getHighscores() {
        l = new Levels("database/levels.txt");
        try {
            laadScores();
            Score[] high = new Score[l.aantalLevels()];
            for (int i = 0; i < l.aantalLevels(); i++) {
                high[i] = new Score("", i + 1, 0);
            }
            for(Score o : scoreList) {
                int level = o.getLevel();
                if(high[level - 1].getScore() == 0 || high[level - 1].getScore() > o.getScore()) {
                    high[level - 1] = o;
                }
            }
            return high;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static void addScore(String gebruiker, int level, int score) {
        scoreList.add(new Score(gebruiker, level, score));
    }

    public static void saveScores() throws Exception {
        String output = "";
        for(Score s : scoreList) {
            output += s.getGebruiker() + "|" + s.getLevel() + "|" + s.getScore() + "\n";
        }

        Formatter uitvoer = new Formatter(path);
        uitvoer.format("%s", Encrypt.encrypt(output));
        uitvoer.close();
        scoreList.clear();
    }
}