package dungeon;

import java.awt.Point;

public class DungeonGame {
    private final DungeonMap dungeonMap;
    private final Player player;
    private final int vision = 1;

    public DungeonGame(DungeonMap map, Player player) {
        this.dungeonMap = map;
        this.player = player;
    }

    public String move(int dx, int dy) {
        if (!player.isAlive() || player.hasWon()) return "";
        if (player.getEnergy() <= 0) {
            player.setAlive(false);
            return "Robotas išsikrovė. Žaidimas baigtas.";
        }

        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        char target = dungeonMap.getCell(newX, newY);

        if (target == '#') return "Siena - negalima eiti ten.";

        player.useEnergy();
        if (player.getEnergy() == 0) {
            player.setAlive(false);
            return "Robotas išsikrovė. Žaidimas baigtas.";
        }

        String message = "";

        switch (target) {
            case 'S' -> {
                player.setAlive(false);
                message = "Robotas pateko į spąstus. Žaidimas baigtas.";
            }
            case 'K' -> {
                player.obtainKey();
                dungeonMap.setCell(newX, newY, ' ');
                message = player.hasKey2()
                        ? "Antras raktas surastas! Ieškokite Durų 2 ir išėjimo."
                        : "Pirmas raktas surastas! Ieškokite Durų 1.";
            }
            case 'D' -> {
                var doors = dungeonMap.getDoors();
                Point d1 = doors.get(0), d2 = doors.get(1);
                if (newX == d1.x && newY == d1.y) {
                    if (player.hasKey1()) {
                        dungeonMap.setCell(newX, newY, ' ');
                        message = "Durys 1 atrakintos!";
                    } else return "Durys 1 užrakintos. Reikia pirmo rakto.";
                } else if (newX == d2.x && newY == d2.y) {
                    if (player.hasKey2()) {
                        dungeonMap.setCell(newX, newY, ' ');
                        message = "Durys 2 atrakintos! Išėjimas atviras.";
                    } else return "Durys 2 užrakintos. Reikia antro rakto.";
                }
            }
            case 'E' -> {
                player.setWon(true);
                message = "Sveikiname! Pasiektas išėjimas!";
            }
        }

        // --- VISADA perkeliame žaidėją, jei jis dar gyvas ---
        if (player.isAlive()) {
            dungeonMap.setCell(player.getX(), player.getY(), ' ');
            player.move(dx, dy);
            dungeonMap.setCell(player.getX(), player.getY(), 'R');
        }

        return message;
    }


    public DungeonMap getMap() { return dungeonMap; }
    public Player getPlayer() { return player; }
    public int getVision() { return vision; }
}
