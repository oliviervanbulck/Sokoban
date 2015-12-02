package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Olivier Van Bulck
 * Date: 23/11/12
 */
public class Levels {
    private ArrayList<Veld[][]> levelField = new ArrayList<Veld[][]>(); // [1 (arraylist)][2][3] 1 = nummer van level; 2 = hoogte van level; 3 = breedte van level

    public Levels(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileReader(fileName));
            boolean newLevel = true;
            String outputLevel = "";

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(newLevel){
                    if(!line.trim().equals("")) {
                        outputLevel = line + "\n";
                        newLevel = false;
                    }
                }
                else if(line.trim().equals("")) {
                    Scanner scannerout = new Scanner(outputLevel);
                    int aantalLijnen = 0;
                    int aantalKolommen = 0;
                    boolean isRow1 = true;
                    while(scannerout.hasNextLine()) {
                        aantalLijnen += 1;
                        String lijn = scannerout.nextLine();
                        if(isRow1) {
                            aantalKolommen = lijn.length();
                            isRow1 = false;
                        }
                    }

                    scannerout = new Scanner(outputLevel);
                    Veld[][] array = new Veld[aantalLijnen][aantalKolommen];
                    for(Veld[] veldje : array) {
                        Arrays.fill(veldje, Veld.LEEG);
                    }

                    int j = 0;
                    while(scannerout.hasNextLine()) {
                        String lijn = scannerout.nextLine();
                        for(int i = 0; i < lijn.length(); i++) {
                            switch(lijn.charAt(i)) {
                                case '$': array[j][i] = Veld.KIST;break;
                                case '@': array[j][i] = Veld.SPELER;break;
                                case '*': array[j][i] = Veld.KISTDOEL;break;
                                case '.': array[j][i] = Veld.DOEL;break;
                                case '#': array[j][i] = Veld.MUUR;break;
                                default : array[j][i] = Veld.LEEG;
                            }
                        }
                        j++;
                    }

                    this.levelField.add(array);
                    newLevel = true;
                }
                else {
                    outputLevel += line + "\n";
                }
            }
        }
        catch(FileNotFoundException e){}
    }

    public Veld[][] getLevel(int levelNummer) {
        return levelField.get(levelNummer - 1);
    }

    public int aantalLevels() {
        return levelField.size();
    }
}