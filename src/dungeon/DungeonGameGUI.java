package dungeon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DungeonGameGUI extends JFrame {
    private final DungeonGame game;
    private final JLabel[][] labels;
    private final JLabel statusLabel;
    private final JLabel energyLabel;

    public DungeonGameGUI(DungeonGame game) {
        this.game = game;
        setTitle("Pozemiu robotas: Raktai, Durys ir Energija");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DungeonMap map = game.getMap();
        labels = new JLabel[map.getRows()][map.getCols()];
        JPanel panel = new JPanel(new GridLayout(map.getRows(), map.getCols()));

        Font font = new Font("Consolas", Font.BOLD, 16);
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                JLabel label = new JLabel(" ", SwingConstants.CENTER);
                label.setFont(font);
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(32, 32));
                labels[i][j] = label;
                panel.add(label);
            }
        }

        energyLabel = new JLabel("Energija: " + game.getPlayer().getEnergy(), SwingConstants.CENTER);
        statusLabel = new JLabel("Suraskite raktus ir atrakinkite duris, kad išeitumėte.", SwingConstants.CENTER);

        add(energyLabel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (!game.getPlayer().isAlive() || game.getPlayer().hasWon()) return;
                String msg = switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> game.move(-1, 0);
                    case KeyEvent.VK_DOWN -> game.move(1, 0);
                    case KeyEvent.VK_LEFT -> game.move(0, -1);
                    case KeyEvent.VK_RIGHT -> game.move(0, 1);
                    default -> "";
                };
                updateMap();
                if (!msg.isEmpty()) statusLabel.setText(msg);
                energyLabel.setText("Energija: " + game.getPlayer().getEnergy());
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        updateMap();
    }

    private void updateMap() {
        char[][] map = game.getMap().getMap();
        Player p = game.getPlayer();
        int vision = game.getVision();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int dx = Math.abs(i - p.getX());
                int dy = Math.abs(j - p.getY());
                if (dx <= vision && dy <= vision) {
                    labels[i][j].setText(String.valueOf(map[i][j]));
                    colorCell(labels[i][j], map[i][j]);
                } else {
                    labels[i][j].setText("");
                    labels[i][j].setBackground(new Color(70, 70, 70));
                }
            }
        }
    }

    private void colorCell(JLabel cell, char c) {
        switch (c) {
            case '#' -> cell.setBackground(new Color(50, 50, 50));
            case 'R' -> cell.setBackground(new Color(102, 178, 255));
            case 'K' -> cell.setBackground(new Color(255, 255, 100));
            case 'D' -> cell.setBackground(new Color(160, 82, 45));
            case 'E' -> cell.setBackground(new Color(100, 200, 100));
            case 'S' -> cell.setBackground(new Color(255, 0, 0));
            default -> cell.setBackground(Color.WHITE);
        }
    }
}
