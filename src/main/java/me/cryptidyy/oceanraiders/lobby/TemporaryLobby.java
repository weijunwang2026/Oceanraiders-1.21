package me.cryptidyy.oceanraiders.lobby;

import org.bukkit.Location;

public class TemporaryLobby {

    private Location corner1;
    private Location corner2;
    private Location spawn;

    public TemporaryLobby()
    {
        corner1 = null;
        corner2 = null;
        spawn = null;
    }

    public TemporaryLobby(Lobby lobby)
    {
        corner1 = lobby.getCorner1();
        corner2 = lobby.getCorner2();
        spawn = lobby.getSpawn();
    }

    public Lobby toLobby()
    {
        if(corner1 == null || corner2 == null || spawn == null) return null;
        return new Lobby(corner1, corner2, spawn);
    }

    public void setCorner1(Location loc)
    {
        corner1 = loc;
    }
    public void setCorner2(Location loc)
    {
        corner2 = loc;
    }
    public void setSpawn(Location loc)
    {
        spawn = loc;
    }
}
