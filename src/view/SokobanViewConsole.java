package view;

import model.GebruikersIO;
import model.Sokoban;
import model.Speelveld;
import model.Veld;

import java.util.Scanner;

/**
 * Olivier Van Bulck
 * Date: 13/11/12
 */
public class SokobanViewConsole {
    private Sokoban game = new Sokoban();
    private Scanner scanner = new Scanner(System.in);

    public void startSpel() {
        int keuze;
        System.out.println("Welkom bij Sokoban! Wat wil je doen?");
        do
        {
            keuze = menuKeuze();
            switch(keuze) {
                case 1: login();break;
                case 2: createUser();break;
                case 3: System.out.println("Deze optie is nog niet uitgewerkt.");break;
                case 4: System.out.println(game.getUitleg());break;
                case 5: if(game.getBackgroundMusic().hasError() == false){if(game.getBackgroundMusic().isMuziekSpelend() == false){game.getBackgroundMusic().start();}else{game.getBackgroundMusic().stop();}}else{System.out.println("Gelieve een juiste menu-optie te kiezen.");}break;
                case 6: if(game.getBackgroundMusic().hasError() == false){if(game.getBackgroundMusic().isMuziekSpelend()){game.getBackgroundMusic().stop();}}System.out.println("Tot de volgende keer!");break;
                default: System.out.println("Gelieve een juiste menu-optie te kiezen.");
            }
        }while(keuze != 6);
    }

    private int menuKeuze() {
        int keuze;
        System.out.println("1. Login");
        System.out.println("2. Nieuwe gebruiker");
        System.out.println("3. Highscores");
        System.out.println("4. Spelregels");
        if(game.getBackgroundMusic().hasError() == false){if(game.getBackgroundMusic().isMuziekSpelend() == false){System.out.println("5. Start muziek");}else{System.out.println("5. Stop muziek");}}
        System.out.println("6. Einde");
        keuze = scanner.nextInt();
        return keuze;
    }

    private void login() {
        System.out.print("Wat is je gebruikersnaam? ");
        String user = scanner.next();
        System.out.print("Gelieve een wachtwoord in te voeren: ");
        String pasw = scanner.next();
        try {
            game.login(user, pasw);
        } catch (Exception e) {
            System.out.println("Er werd een bestand niet gevonden.");
        }

        if(game.checkLogin()) {
            startLoginMenu();
        }
        else {
            System.out.println("Deze combinatie van gebruikersnaam en wachtwoord is niet correct.");
        }
    }

    private void createUser() {
        System.out.print("Welke gebruikersnaam zou je graag hebben? ");
        String user = scanner.next();
        System.out.print("Gelieve een wachtwoord in te voeren: ");
        String pasw = scanner.next();
        game.createUser(user, pasw);

        if(game.checkLogin()) {
            startLoginMenu();
        }
        else {
            System.out.println("Er is een fout opgetreden bij het aanmaken van de gebruiker. (" + GebruikersIO.getError() + ")");
        }
    }

    private void startLoginMenu() {
        System.out.println("Welkom " + game.getLoggedInUser().getUsername() + "! Wat wil je doen?");
        int keuze = 0;
        do
        {
            keuze = menuKeuzeLogin();
            switch(keuze) {
                case 1: speelSpel(0);break;
                case 2: kiesLevel();break;
                case 3: System.out.println(game.getUitleg());break;
                case 4: System.out.println("Deze optie werd nog niet uitgewerkt.");break;
                case 5:
                    try {
                        game.logout();
                    } catch (Exception e) {
                        System.out.println("Er werd een bestand niet gevonden.");
                    }
                    break;
                default: System.out.println("Gelieve een juiste menu-optie te kiezen.");
            }
        }while(keuze != 5);
    }

    private int menuKeuzeLogin() {
        System.out.println("1. Verdergaan met volgende level: " + game.getLoggedInUser().getLevel());
        System.out.println("2. Level kiezen");
        System.out.println("3. Spelregels");
        System.out.println("4. Highscores");
        System.out.println("5. Uitloggen");
        return scanner.nextInt();
    }

    private void speelSpel(int level) {
        boolean started = false;
        if(level == 0) {
            started = game.startSpel();
        }
        else {
            started = game.startSpel(level);
        }
        if(started) {
            char newChar = ' ';
            boolean isGewonnen;
            System.out.printf("Welkom bij level %d. Gebruik q, z, s en d om te bewegen. Je kan het spel beëindigen door e in te drukken. Veel succes!\n", game.getLoggedInUser().getLevel());
            do {
                Speelveld veld = game.getSpeelveld();
                System.out.println("Je hebt al " + veld.getStappen() + " stappen gezet.");
                System.out.println(veld.getSpelerLocatieString());
                for(int i = 0; i < veld.getVeld().length; i++) {
                    for(int j = 0; j < veld.getVeld()[i].length; j++) {
                        switch(veld.getVeld()[i][j]) {
                            case MUUR: System.out.print("#");break;
                            case DOEL: System.out.print((veld.getSpelerLocatie()[0] == i && veld.getSpelerLocatie()[1] == j)?"@":".");break;
                            case KISTDOEL: System.out.print("$");break;
                            case KIST: System.out.print("$");break;
                            default: System.out.print((veld.getSpelerLocatie()[0] == i && veld.getSpelerLocatie()[1] == j)?"@":" ");
                        }
                    }
                    System.out.println();
                }
                isGewonnen = game.isGewonnen(false);
                if(!isGewonnen) {
                    newChar = scanner.next().charAt(0);
                    switch(newChar) {
                        case 'z': game.moveUp();break;
                        case 'q': game.moveLeft();break;
                        case 's': game.moveDown();break;
                        case 'd': game.moveRight();break;
                    }
                }
            } while(!isGewonnen && newChar != 'e');
            if(isGewonnen == true) {
                System.out.printf("Je hebt dit level uitgespeeld! Proficiat! Je gaat nu door naar level %d.\n", game.getLoggedInUser().getLevel());
            }
        }
        else {
            System.out.println("Er is een fout opgetreden bij het starten van het spel.");
        }
    }

    public void kiesLevel() {
        int newLevel;
        do {
            System.out.print("Geef het nummer van het level in dat je wilt spelen (0 om te stoppen): ");
            newLevel = scanner.nextInt();
        } while(newLevel < 0 || newLevel > 50);
        if(newLevel != 0) speelSpel(newLevel);
    }
}