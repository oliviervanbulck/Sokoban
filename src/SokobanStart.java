import model.HighscoreIO;
import view.*;

/**
 * Olivier Van Bulck
 * Date: 13/11/12
 */
public class SokobanStart {
    public static void main(String[] args) {
        //SokobanViewConsole game = new SokobanViewConsole();
        SokobanViewSwing game = new SokobanViewSwing();
        game.startSpel();
    }
}