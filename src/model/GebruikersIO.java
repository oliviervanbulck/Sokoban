package model;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Olivier Van Bulck
 * Date: 23/11/12
 */
public class GebruikersIO {
    private static final String path = "database/gebruikers.enc";
    private static Scanner scanner;
    private static String error = "";
    private static ArrayList<Speler> userList = new ArrayList<Speler>();
    private static int user = -1; //-1 is niet ingelogd. Eerste gebruiker is immers 0.

    public static boolean login(String username, String password) throws Exception {
        boolean isSpelerGevonden = false;
        password = getPasswordHash(password);
        if(password.equals("")) { error = "Er is iets fout gelopen bij het beveiligen van het wachtwoord.";return false; }
        File f = new File(path);
        scanner =  new Scanner(new FileReader(f));
        String input = "";
        String[] speler;
        while(scanner.hasNextLine()) {
            input += scanner.nextLine();
        }
        scanner = new Scanner(Encrypt.decrypt(input));
        while (scanner.hasNextLine()){
            //controle of gebruiker overeenkomt
            String line = scanner.nextLine();
            String test = username + "|" + password + "|";
            if(line.length() >= test.length()) {
                if(line.substring(0, test.length()).equals(test)) {
                    user = userList.size();
                    isSpelerGevonden = true;
                }
            }
            speler = line.split("\\|");
            userList.add(new Speler(speler[0], speler[1], Integer.parseInt(speler[2])));
        }
        return isSpelerGevonden;
    }

    public static boolean createUser(String username, String password) {
        if(isLoggedIn()) {return false;}
        boolean gevonden = false;
        try {
            scanner = new Scanner(new FileReader(path));
            String input = "";
            String[] speler;
            while(scanner.hasNextLine()) {
                input += scanner.nextLine();
            }
            scanner = new Scanner(Encrypt.decrypt(input));
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.length() >= username.length()) {
                    if(line.substring(0, username.length()).equals(username)) {
                        error = "Deze gebruikersnaam bestaat al.";
                        gevonden = true;
                    }
                }
                speler = line.split("\\|");
                userList.add(new Speler(speler[0], speler[1], Integer.parseInt(speler[2])));
            }
        }catch(IOException IOE){return false;}
         catch(Exception e){return false;}

        if(gevonden) {
            userList.clear();
            user = -1;
        }
        else {
            userList.add(new Speler(username, getPasswordHash(password), 1));
            user = userList.size() - 1;
        }

        return !gevonden;
    }

    public static String getError() {
        return error;
    }

    private static String getPasswordHash(String password) {
        MessageDigest m;
        try {m = MessageDigest.getInstance("SHA");}catch(NoSuchAlgorithmException NSA){error = "Er is een fout opgetreden bij het proberen aan te melden van de gebruiker.";return "";}
        m.reset();
        m.update(password.getBytes()); //String to bytes
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

    public static void logout() {
        if(isLoggedIn()) {
            String output = "";
            for(Speler s : userList) {
                output += s.getUsername() + "|" + s.getEncPassword() + "|" + String.format("%02d", s.getLevel()) + "\n";
            }
            try {
                Formatter uitvoer = new Formatter("database/gebruikers.enc");
                uitvoer.format("%s", Encrypt.encrypt(output));
                uitvoer.close();
                userList.clear();
                user = -1;
            } catch(FileNotFoundException fnf) {}
              catch(Exception e){}
        }
    }

    public static Speler getLoggedInUser() {
        return userList.get(user);
    }

    public static boolean isLoggedIn() {
        return user != -1;
    }
}