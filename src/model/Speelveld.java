package model;

/**
 * Olivier Van Bulck
 * Date: 23/11/12
 */
public class Speelveld {
    private Veld[][] speelveld;
    private int[] spelerLocatie = new int[2];
    private int stappen = 0;
    private Muziek opl = new Muziek("sound/oplocatie.wav");
    private int aantalLevels;

    public Speelveld(Level model) {
        speelveld = new Veld[model.getLevel().length][model.getLevel()[0].length];
        this.aantalLevels = model.getAantalLevels();
        for(int i = 0; i < model.getLevel().length; i++) {
            for(int j = 0; j < model.getLevel()[i].length; j++) {
                speelveld[i][j] = Veld.LEEG;
                switch(model.getLevel()[i][j]) {
                    case SPELER: spelerLocatie[0] = i;spelerLocatie[1] = j;break;
                    case KIST  : speelveld[i][j] = Veld.KIST;break;
                    case DOEL  : speelveld[i][j] = Veld.DOEL;break;
                    case MUUR  : speelveld[i][j] = Veld.MUUR;break;
                    case KISTDOEL: speelveld[i][j] = Veld.KISTDOEL;break;
                }
            }
        }
    }

    public Veld[][] getVeld() {
        return this.speelveld;
    }

    public void moveLeft() {
        int x = spelerLocatie[1];
        int y = spelerLocatie[0];

        if(x > 0 && (speelveld[y][x - 1] == Veld.LEEG || speelveld[y][x - 1] == Veld.DOEL)) {
            x--;
            stappen++;
        }
        else if(x > 1 && (speelveld[y][x - 2] == Veld.LEEG || speelveld[y][x - 2] == Veld.DOEL) && (speelveld[y][x - 1] == Veld.KIST || speelveld[y][x - 1] == Veld.KISTDOEL)) {
            if(speelveld[y][x - 2] == Veld.DOEL && speelveld[y][x - 1] == Veld.KIST) {
                speelveld[y][x - 2] = Veld.KISTDOEL;
                speelveld[y][x - 1] = Veld.LEEG;
                x--;
                stappen++;
                opl.start();
            }
            else if(speelveld[y][x - 2] == Veld.LEEG && speelveld[y][x - 1] == Veld.KIST) {
                speelveld[y][x - 2] = Veld.KIST;
                speelveld[y][x - 1] = Veld.LEEG;
                x--;
                stappen++;
            }
            else if(speelveld[y][x - 2] == Veld.DOEL && speelveld[y][x - 1] == Veld.KISTDOEL) {
                speelveld[y][x - 2] = Veld.KISTDOEL;
                speelveld[y][x - 1] = Veld.DOEL;
                x--;
                stappen++;
                opl.start();
            }
            else if(speelveld[y][x - 2] == Veld.LEEG && speelveld[y][x - 1] == Veld.KISTDOEL) {
                speelveld[y][x - 2] = Veld.KIST;
                speelveld[y][x - 1] = Veld.DOEL;
                x--;
                stappen++;
            }
        }

        spelerLocatie[1] = x;
        spelerLocatie[0] = y;
    }

    public void moveRight() {
        int x = spelerLocatie[1];
        int y = spelerLocatie[0];

        if(x < speelveld[0].length - 1 && (speelveld[y][x + 1] == Veld.LEEG || speelveld[y][x + 1] == Veld.DOEL)) {
            x++;
            stappen++;
        }
        else if(x < speelveld[0].length - 2 && (speelveld[y][x + 2] == Veld.LEEG || speelveld[y][x + 2] == Veld.DOEL) && (speelveld[y][x + 1] == Veld.KIST || speelveld[y][x + 1] == Veld.KISTDOEL)) {
            if(speelveld[y][x + 2] == Veld.DOEL && speelveld[y][x + 1] == Veld.KIST) {
                speelveld[y][x + 2] = Veld.KISTDOEL;
                speelveld[y][x + 1] = Veld.LEEG;
                x++;
                stappen++;
                opl.start();
            }
            else if(speelveld[y][x + 2] == Veld.LEEG && speelveld[y][x + 1] == Veld.KIST) {
                speelveld[y][x + 2] = Veld.KIST;
                speelveld[y][x + 1] = Veld.LEEG;
                x++;
                stappen++;
            }
            else if(speelveld[y][x + 2] == Veld.DOEL && speelveld[y][x + 1] == Veld.KISTDOEL) {
                speelveld[y][x + 2] = Veld.KISTDOEL;
                speelveld[y][x + 1] = Veld.DOEL;
                x++;
                stappen++;
                opl.start();
            }
            else if(speelveld[y][x + 2] == Veld.LEEG && speelveld[y][x + 1] == Veld.KISTDOEL) {
                speelveld[y][x + 2] = Veld.KIST;
                speelveld[y][x + 1] = Veld.DOEL;
                x++;
                stappen++;
            }
        }

        spelerLocatie[1] = x;
        spelerLocatie[0] = y;
    }

    public void moveUp() {
        int x = spelerLocatie[1];
        int y = spelerLocatie[0];

        if(y > 0 && (speelveld[y - 1][x] == Veld.LEEG || speelveld[y - 1][x] == Veld.DOEL)) {
            y--;
            stappen++;
        }
        else if(y > 1 && (speelveld[y - 2][x] == Veld.LEEG || speelveld[y - 2][x] == Veld.DOEL) && (speelveld[y - 1][x] == Veld.KIST || speelveld[y - 1][x] == Veld.KISTDOEL)) {
            if(speelveld[y - 2][x] == Veld.DOEL && speelveld[y - 1][x] == Veld.KIST) {
                speelveld[y - 2][x] = Veld.KISTDOEL;
                speelveld[y - 1][x] = Veld.LEEG;
                y--;
                stappen++;
                opl.start();
            }
            else if(speelveld[y - 2][x] == Veld.LEEG && speelveld[y - 1][x] == Veld.KIST) {
                speelveld[y - 2][x] = Veld.KIST;
                speelveld[y - 1][x] = Veld.LEEG;
                y--;
                stappen++;
            }
            else if(speelveld[y - 2][x] == Veld.DOEL && speelveld[y - 1][x] == Veld.KISTDOEL) {
                speelveld[y - 2][x] = Veld.KISTDOEL;
                speelveld[y - 1][x] = Veld.DOEL;
                y--;
                stappen++;
                opl.start();
            }
            else if(speelveld[y - 2][x] == Veld.LEEG && speelveld[y - 1][x] == Veld.KISTDOEL) {
                speelveld[y - 2][x] = Veld.KIST;
                speelveld[y - 1][x] = Veld.DOEL;
                y--;
                stappen++;
            }
        }

        spelerLocatie[1] = x;
        spelerLocatie[0] = y;
    }

    public void moveDown() {
        int x = spelerLocatie[1];
        int y = spelerLocatie[0];

        if(y < speelveld[0].length - 1 && (speelveld[y + 1][x] == Veld.LEEG || speelveld[y + 1][x] == Veld.DOEL)) {
            y++;
            stappen++;
        }
        else if((speelveld[y + 1][x] == Veld.KIST || speelveld[y + 1][x] == Veld.KISTDOEL) && y < speelveld[0].length - 2 && (speelveld[y + 2][x] == Veld.LEEG || speelveld[y + 2][x] == Veld.DOEL)) {
            if(speelveld[y + 2][x] == Veld.DOEL && speelveld[y + 1][x] == Veld.KIST) {
                speelveld[y + 2][x] = Veld.KISTDOEL;
                speelveld[y + 1][x] = Veld.LEEG;
                y++;
                stappen++;
                opl.start();
            }
            else if(speelveld[y + 2][x] == Veld.LEEG && speelveld[y + 1][x] == Veld.KIST) {
                speelveld[y + 2][x] = Veld.KIST;
                speelveld[y + 1][x] = Veld.LEEG;
                y++;
                stappen++;
            }
            else if(speelveld[y + 2][x] == Veld.DOEL && speelveld[y + 1][x] == Veld.KISTDOEL) {
                speelveld[y + 2][x] = Veld.KISTDOEL;
                speelveld[y + 1][x] = Veld.DOEL;
                y++;
                stappen++;
                opl.start();
            }
            else if(speelveld[y + 2][x] == Veld.LEEG && speelveld[y + 1][x] == Veld.KISTDOEL) {
                speelveld[y + 2][x] = Veld.KIST;
                speelveld[y + 1][x] = Veld.DOEL;
                y++;
                stappen++;
            }
        }

        spelerLocatie[1] = x;
        spelerLocatie[0] = y;
    }

    public boolean isGewonnen(boolean c) {
        for(int i = 0; i< speelveld.length; i++) {
            for(int j = 0; j < speelveld[i].length; j++) {
                if(speelveld[i][j] == Veld.DOEL) {
                    return false;
                }
            }
        }

        HighscoreIO.addScore(GebruikersIO.getLoggedInUser().getUsername(), GebruikersIO.getLoggedInUser().getLevel(), stappen);
        if(!c && GebruikersIO.getLoggedInUser().getLevel() < aantalLevels){GebruikersIO.getLoggedInUser().incrementLevel();}
        Muziek win = new Muziek("sound/win.wav");
        win.start();
        return true;
    }

    public int[] getSpelerLocatie() {
        return spelerLocatie;
    }

    public String getSpelerLocatieString() {
        return spelerLocatie[0] + "," + spelerLocatie[1];
    }

    public int getStappen() {
        return this.stappen;
    }

    public int getAantalLevels() {
        return aantalLevels;
    }
}
