package dungeon;

public class Player {
    private int x;
    private int y;
    private boolean hasKey1;
    private boolean hasKey2;
    private boolean alive = true;
    private boolean won = false;
    private int energy = 80;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void move(int dx, int dy) {
        x += dx; y += dy;
    }
    public boolean isAlive() {
        return alive;
    }
    public boolean hasWon() {
        return won;
    }
    public int getEnergy() {
        return energy;
    }
    public boolean hasKey1() {
        return hasKey1;
    }
    public boolean hasKey2() {
        return hasKey2;
    }

    public void setAlive(boolean a) {
        alive = a;
    }
    public void setWon(boolean w) {
        won = w;
    }
    public void useEnergy() {
        energy--;
    }

    public void obtainKey() {
        if (!hasKey1) hasKey1 = true;
        else if (!hasKey2) hasKey2 = true;
    }
}
