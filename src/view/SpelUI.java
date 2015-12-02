package view;

import model.Sokoban;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Olivier Van Bulck
 * Date: 1/03/13
 */
public class SpelUI extends JFrame {
    private LoggedInUI previous;
    private Sokoban game;
    private JMenuBar mbMenuBar = new JMenuBar();
    private JMenu mnuFile = new JMenu("Menu");
    private JMenuItem mniSpelregels = new JMenuItem("Spelregels");
    private JMenuItem mniHighscores = new JMenuItem("Highscores");
    private JMenuItem mniHerstart = new JMenuItem("Herstart level");
    private JMenuItem mniStoppen = new JMenuItem("Stoppen");
    private JPanel pnlGame;
    private JLabel lblStappen = new JLabel();
    private final URL DOEL = getClass().getResource("../images/target.png");
    private final URL KISTDOEL = getClass().getResource("../images/blocktarget.jpg");
    private final URL KIST = getClass().getResource("../images/block.jpg");
    private final URL MUUR = getClass().getResource("../images/wall.jpg");
    private int level = 1;
    private URL userpic = getClass().getResource("../images/user/1.png");
    private boolean chosen;

    public SpelUI(Sokoban game, LoggedInUI previous, int level, URL userpic, boolean chosen) {
        this.game = game;
        this.previous = previous;
        this.level = level;
        this.userpic = userpic;
        this.chosen = chosen;
        boolean started;
        if(level == 0) {
            started = game.startSpel();
        }
        else {
            started = game.startSpel(level);
        }
        if(started == false) {
            dispose();
            this.previous.setVisible(true);
        }
        else {
            setSize(200, 400);
            setTitle("Sokoban - Spel");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent evt) {
                    SpelUI.this.previous.setVisible(true);
                }
            });
            add(mbMenuBar, BorderLayout.NORTH);
            mbMenuBar.add(mnuFile);
            mnuFile.add(mniSpelregels);
            mnuFile.add(mniHighscores);
            mnuFile.addSeparator();
            mnuFile.add(mniHerstart);
            mniSpelregels.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(SpelUI.this, SpelUI.this.game.getUitleg(), "Spelregels", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            mniHerstart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SpelUI.this.game.startSpel(SpelUI.this.level);
                    SpelUI.this.revalidate();
                }
            });
            mniStoppen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SpelUI.this.dispose();
                    SpelUI.this.previous.revalidate();
                    SpelUI.this.previous.setVisible(true);
                }
            });
            mnuFile.add(mniStoppen);
            mbMenuBar.setVisible(true);
            repaintPanel(true);
            addKeyListener(new GUIKeyListener());
            pack();
            setResizable(false);
            setVisible(true);
        }
    }

    public void repaintPanel(boolean isFirst) {
        if(isFirst == false) {this.remove(pnlGame);pnlGame = null;}
        pnlGame = new JPanel(new GridLayout(game.getSpeelveld().getVeld().length, game.getSpeelveld().getVeld()[0].length));
        for(int i = 0; i < game.getSpeelveld().getVeld().length; i++) {
            for(int j = 0; j < game.getSpeelveld().getVeld()[0].length; j++) {
                JLabel lbl = new JLabel();
                lbl.setSize(32, 32);
                switch (game.getSpeelveld().getVeld()[i][j]) {
                    case DOEL: lbl.setIcon(new ImageIcon(DOEL));break;
                    case KISTDOEL: lbl.setIcon(new ImageIcon(KISTDOEL));break;
                    case KIST: lbl.setIcon(new ImageIcon(KIST));break;
                    case MUUR: lbl.setIcon(new ImageIcon(MUUR));break;
                }
                int[] vars = game.getSpeelveld().getSpelerLocatie();
                if(j == vars[1] && i == vars[0]) {
                    lbl.setIcon(new ImageIcon(userpic));
                }
                lbl.setVisible(true);
                lbl.setOpaque(true);
                pnlGame.add(lbl);
            }
        }
        add(pnlGame, BorderLayout.CENTER);
        lblStappen.setText("Je hebt reeds " + game.getSpeelveld().getStappen() + " stappen gezet.");
        add(lblStappen, BorderLayout.SOUTH);
        //revalidate();
    }

    private class GUIKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT : SpelUI.this.game.moveLeft();break;
                case KeyEvent.VK_RIGHT : SpelUI.this.game.moveRight();break;
                case KeyEvent.VK_UP : SpelUI.this.game.moveUp();break;
                case KeyEvent.VK_DOWN : SpelUI.this.game.moveDown();break;
            }
            SpelUI.this.repaintPanel(false);
            if(SpelUI.this.game.isGewonnen(SpelUI.this.chosen)) {
                JOptionPane.showMessageDialog(SpelUI.this, "Proficiat! Je hebt dit level uitgespeeld!", "Proficiat!", JOptionPane.PLAIN_MESSAGE, new ImageIcon(userpic));
                SpelUI.this.dispose();
                SpelUI.this.previous.revalidate();
                SpelUI.this.previous.setVisible(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    @Override
    public void revalidate() {
        repaintPanel(false);
        super.revalidate();
    }
}