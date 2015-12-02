package model;

/**
 * Olivier Van Bulck
 * Date: 30/11/12
 */
public class Speler {
    private String username;
    private String encPassword;
    private int level;

    public Speler(String username, String password, int level) {
        this.username = username;
        this.encPassword = password;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public String getEncPassword() {
        return encPassword;
    }

    public int getLevel() {
        return level;
    }

    public void incrementLevel() {
        this.level++;
    }
}