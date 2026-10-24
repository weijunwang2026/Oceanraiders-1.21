package me.cryptidyy.oceanraiders;

import me.cryptidyy.coreapi.api.API;
import me.cryptidyy.oceanraiders.gamemap.GameMap;
import me.cryptidyy.oceanraiders.gamemap.LocalGameMap;
import me.cryptidyy.oceanraiders.lobby.LobbyManager;
import me.cryptidyy.oceanraiders.lobby.LobbySetupManager;
import me.cryptidyy.oceanraiders.sql.SQLHelper;
import me.cryptidyy.oceanraiders.state.EndState;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cryptidyy.oceanraiders.commands.GameCommands;
import me.cryptidyy.oceanraiders.commands.SetupCommands;
import me.cryptidyy.oceanraiders.commands.TestCommands;
import me.cryptidyy.oceanraiders.customitems.OceanItemManager;
import me.cryptidyy.oceanraiders.islands.IslandManager;
import me.cryptidyy.oceanraiders.islands.IslandSetupManager;
import me.cryptidyy.oceanraiders.state.GameManager;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

	private IslandManager islandManager;
	private LobbyManager lobbyManager;
	private GameManager gameManager;
	private OceanItemManager itemManager;

	private List<Listener> registeredListeners = new ArrayList<>();
	private SQLHelper sqlHelper;

	private GameMap currentMap;

	@Override
	public void onEnable()
	{
		getDataFolder().mkdir();
		if(!checkIfBungee()) return;

		File gameMapFile = new File(getDataFolder(), "gamemaps");
		if (!gameMapFile.exists())
		{
			gameMapFile.mkdir();
		}
        try {
            this.currentMap = new LocalGameMap(gameMapFile, "oceanraiders", true);
        } catch (Exception e) {
			getLogger().severe("The arena world folder is invalid, please import a world. Shutting down...");
			getServer().getPluginManager().disablePlugin(this);
            return;
        }
		if(!currentMap.isLoaded())
		{
			getLogger().severe("The arena world failed to load. Shutting down...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
        this.gameManager = new GameManager(this);

		Bukkit.getScheduler().runTaskLater(this, () ->
		{
			this.islandManager = new IslandManager(this);
			this.lobbyManager = new LobbyManager(this);
			this.itemManager = new OceanItemManager(this);

			this.getServer().getPluginManager().registerEvents(new IslandSetupManager(islandManager), this);
			this.getServer().getPluginManager().registerEvents(new LobbySetupManager(lobbyManager), this);
			String fullPackageName = this.getClass().getPackage().getName();

			this.getCommand("orsetup").setExecutor(new SetupCommands(this));
			this.getCommand("oceanraiders").setExecutor(new GameCommands(this));
			this.getCommand("setstate").setExecutor(new TestCommands(this));

			//register every event in package
			for(Class<?> clazz : new Reflections(fullPackageName + ".serverlisteners")
					.getSubTypesOf(Listener.class))
			{
				try {
					Listener listener = (Listener) clazz
							.getDeclaredConstructor()
							.newInstance();
					this.getServer().getPluginManager().registerEvents(listener, this);
					registeredListeners.add(listener);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					//e.printStackTrace();
				}
				try {
					Listener listener = (Listener) clazz
							.getDeclaredConstructor(Main.class)
							.newInstance(this);
					this.getServer().getPluginManager().registerEvents(listener, this);
					registeredListeners.add(listener);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					//e.printStackTrace();
				}
			}
			gameManager.onEnable();

			//cannot do this if the table is not first created from Bungee

			//Set server info with coreAPI in RegistryHelper, same as all other games
			//The API sees every server as a GameServer object
			//The API interacts with SQL, SQL is the bridge between bungee plugin and spigot api
			//GameServer object is created by individual servers and then passed as an argument into the static API class
			//GameServer implements GameServerProvider
			//SQL listens for server status updates
			API.getInstance().getStatusUpdater().setStarted(true);
		}, 20 * 2);
	}
	
	@Override
	public void onDisable()
	{
		currentMap.unload();
		HandlerList.unregisterAll(this);
		API.getInstance().getStatusUpdater().setStarted(false);
		API.getInstance().getStatusUpdater().setWaiting(false);

		if(gameManager != null)
		{
			gameManager.onDisable();
		}
	}

	public IslandManager getIslandManager() {
		return islandManager;
	}
	public LobbyManager getLobbyManager() {
		return lobbyManager;
	}
	public GameManager getGameManager() {
		return this.gameManager;
	}
	public OceanItemManager getItemManager() {
		return itemManager;
	}

	private boolean checkIfBungee()
	{
		if (!getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean("bungeecord"))
		{
			getLogger().severe( "This server is not BungeeCord." );
			getLogger().severe( "If the server is already hooked to BungeeCord, please enable it in your spigot.yml as well." );
			getLogger().severe( "Shutting down..." );
			getServer().getPluginManager().disablePlugin(this);
			return false;
		}
		return true;
	}
	private static String getServerName(DataManager dataManager)
	{
		return dataManager.getConfig().getString("servername");
	}
	public SQLHelper getSqlHelper()
	{
		return this.sqlHelper;
	}

	public GameMap getCurrentMap()
	{
		return currentMap;
	}
}
