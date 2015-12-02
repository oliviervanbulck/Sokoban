package view;

import model.Sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 * Olivier Van Bulck
 * Date: 13/03/13
 */
public class IconChooser extends JFrame {
    private JPanel pnlGrid = new JPanel(new GridLayout(7, 7));
    private LoggedInUI prev;
    private Sokoban game;
    private int level;
    private boolean chosen = true;

    public IconChooser(LoggedInUI prev, Sokoban game) {
        this(prev, game, game.getLoggedInUser().getLevel());
        this.chosen = false;
    }
    public IconChooser(LoggedInUI prev, Sokoban game, int level) {
        this.level = level;
        this.prev = prev;
        this.game = game;
        pnlGrid.setLayout(new GridLayout(7, 7));
        for(int i = 1; i < 50; i++) {
            IconLabel lbl = new IconLabel(i);
            lbl.setSize(32, 32);
            lbl.setIcon(new ImageIcon(getClass().getResource("../images/user/" + i + ".png")));
            lbl.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    IconLabel il = (IconLabel)e.getSource();
                    SpelUI ui = new SpelUI(IconChooser.this.game, IconChooser.this.prev, IconChooser.this.level, getClass().getResource("../images/user/"+il.getNumber()+".png"), IconChooser.this.chosen);
                    IconChooser.this.dispose();
                }
            });
            lbl.setOpaque(true);
            pnlGrid.add(lbl);
        }
        setTitle("Kies je icoontje:");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(pnlGrid, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
