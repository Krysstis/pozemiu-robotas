package dungeon;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DungeonMap {
    private char[][] map;
    private List<Point> keyPositions = new ArrayList<>();
    private List<Point> doorPositions = new ArrayList<>();
    private Point exitPos;

    public DungeonMap(char[][] baseMap) {
        this.map = baseMap;
        scanMap();
    }

    private void scanMap() {
        keyPositions.clear();
        doorPositions.clear();
        exitPos = null;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 'K' -> keyPositions.add(new Point(i, j));
                    case 'D' -> doorPositions.add(new Point(i, j));
                    case 'E' -> exitPos = new Point(i, j);
                }
            }
        }
    }

    public char getCell(int x, int y) { return map[x][y]; }
    public void setCell(int x, int y, char c) { map[x][y] = c; }
    public int getRows() { return map.length; }
    public int getCols() { return map[0].length; }

    public List<Point> getKeys() { return keyPositions; }
    public List<Point> getDoors() { return doorPositions; }
    public Point getExit() { return exitPos; }

    public char[][] getMap() { return map; }
}
