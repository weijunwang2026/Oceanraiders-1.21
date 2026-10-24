package me.cryptidyy.oceanraiders.lobby;

import org.bukkit.Location;

public class Lobby {

    private final Location corner1;
    private final Location corner2;
    private final Location spawn;

    public Lobby(Location corner1, Location corner2, Location spawn)
    {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.spawn = spawn;
    }

    public Location getCorner1() {
        return corner1;
    }

    public Location getCorner2() {
        return corner2;
    }

    public Location getSpawn() {
        return spawn;
    }
}
