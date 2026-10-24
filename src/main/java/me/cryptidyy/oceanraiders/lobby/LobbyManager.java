package me.cryptidyy.oceanraiders.lobby;

import me.cryptidyy.oceanraiders.DataManager;
import me.cryptidyy.oceanraiders.Main;
import me.cryptidyy.oceanraiders.utility.DataConfigUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LobbyManager {

    private Lobby lobby;
    private DataManager lobbyData;
    private Main plugin;
    private LobbySetupManager lobbySetupManager;
    public LobbyManager(Main plugin)
    {
        this.plugin = plugin;
        lobbyData = new DataManager(plugin, "lobby.yml");
        this.lobbySetupManager = new LobbySetupManager(this);
        reloadLobby();
    }

    public void reloadLobby()
    {
        lobbyData.reloadConfig();
        ConfigurationSection config = lobbyData.getConfig();
        Location corner1 = DataConfigUtil.readLocation(config.getConfigurationSection("corner_one"));
        Location corner2 = DataConfigUtil.readLocation(config.getConfigurationSection("corner_two"));
        Location spawn = DataConfigUtil.readLocation(config.getConfigurationSection("spawn"));

        lobby = new Lobby(corner1, corner2, spawn);
    }

    public void saveLobby(Lobby lobby)
    {
        this.lobby = lobby;
        FileConfiguration config = lobbyData.getConfig();

        DataConfigUtil.saveLocation(lobby.getCorner1(), config.createSection("corner_one"));
        DataConfigUtil.saveLocation(lobby.getCorner2(), config.createSection("corner_two"));
        DataConfigUtil.saveLocation(lobby.getSpawn(), config.createSection("spawn"));

        lobbyData.saveConfig();
    }

    public Lobby getLobby()
    {
        return lobby;
    }

    public LobbySetupManager getLobbySetupManager()
    {
        return lobbySetupManager;
    }
}
