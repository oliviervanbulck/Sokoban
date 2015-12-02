package model;

/**
 * Created with IntelliJ IDEA.
 * User: olivier
 * Date: 22-2-13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class Score implements Comparable<Score> {
    private String gebruiker;
    private int level;
    private int score;

    public Score(String gebruiker, int level, int score) {
        this.gebruiker = gebruiker;
        this.level = level;
        this.score = score;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score o) {
        return o.score - this.score;
    }
}
