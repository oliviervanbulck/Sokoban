package model;

/**
 * Olivier Van Bulck
 * Date: 23/11/12
 */
public class Level {
    private Veld[][] level;
    private Levels levels;

    public Level(String levelFile, int levelNummer) {
        this.levels = new Levels(levelFile);
        this.level = levels.getLevel(levelNummer);
    }

    public Veld[][] getLevel() {
        return level;
    }

    public int getAantalLevels() {
        return levels.aantalLevels();
    }
}