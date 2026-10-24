package me.cryptidyy.oceanraiders.lobby;

import me.cryptidyy.oceanraiders.islands.TemporaryIsland;
import me.cryptidyy.oceanraiders.utility.ChatUtil;
import me.cryptidyy.oceanraiders.utility.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbySetupManager implements Listener {

    private static Map<UUID, TemporaryLobby> playerToTempLobby = new HashMap<>();
    private final LobbyManager lobbyManager;

    private final String SET_CORNER_ONE = ChatColor.GREEN + "Set Corner One " + ChatColor.GRAY + "(Right Click)";
    private final String SET_CORNER_TWO = ChatColor.GREEN + "Set Corner Two " + ChatColor.GRAY + "(Right Click)";
    private final String SET_SPAWN = ChatColor.GREEN + "Set Spawnpoint " + ChatColor.GRAY + "(Right Click)";

    private final String SAVE = ChatColor.GREEN + "Save " + ChatColor.GRAY+ "(Right Click)";
    private final String CANCEL = ChatColor.RED+"Cancel " + ChatColor.GRAY+ "(Right Click)";

    public LobbySetupManager(LobbyManager lobbyManager)
    {
        this.lobbyManager = lobbyManager;
    }

    public void addToSetup(Player player, TemporaryLobby tempLobby)
    {
        if (playerToTempLobby.containsKey(player.getUniqueId())) return;
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        playerToTempLobby.put(player.getUniqueId(), tempLobby);

        player.getInventory().addItem(new ItemBuilder(Material.IRON_AXE)
                .setDisplayName(SET_CORNER_ONE).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Material.GOLDEN_AXE)
                .setDisplayName(SET_CORNER_TWO).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Material.IRON_SHOVEL)
                .setDisplayName(SET_SPAWN).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Material.LIME_DYE)
                .setDisplayName(SAVE).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Material.BARRIER)
                .setDisplayName(CANCEL).toItemStack());

        player.sendMessage(ChatColor.GREEN + "Moved to setup mode for waiting lobby");
    }

    public void removeFromSetup(Player player)
    {
        if(!playerToTempLobby.containsKey(player.getUniqueId()))
            return;

        playerToTempLobby.remove(player.getUniqueId());

        player.sendMessage(ChatColor.AQUA + "---Setup finished!---");

        //Restore player
        player.getInventory().clear();
    }

    public boolean inSetupMode(Player player)
    {
        return playerToTempLobby.containsKey(player.getUniqueId());
    }

    public Location[] fixCorners(Location cornerOne, Location cornerTwo)
    {
        double x1 = cornerOne.getX();
        double x2 = cornerTwo.getX();

        double y1 = cornerOne.getY();
        double y2 = cornerTwo.getY();

        double z1 = cornerOne.getZ();
        double z2 = cornerTwo.getZ();


        Location newCornerTwo = new Location(cornerTwo.getWorld(), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
        Location newCornerOne = new Location(cornerOne.getWorld(), Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        return new Location[] {newCornerOne, newCornerTwo};

    }

    Location corner1;
    Location corner2;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event)
    {

        if (!inSetupMode(event.getPlayer())) return;
        if (!event.hasItem()) return;
        //if(!event.getItem().hasItemMeta()) return;

        Player player = event.getPlayer();
        TemporaryLobby tempLobby = playerToTempLobby.get(player.getUniqueId());
        String itemName = event.getItem().getItemMeta().getDisplayName();

        if(itemName.equals(SET_CORNER_ONE))
        {
            corner1 = player.getLocation();
            player.sendMessage(ChatColor.AQUA + "Set lobby corner 1 location!");
        }
        else if(itemName.equals(SET_CORNER_TWO))
        {
            corner2 = player.getLocation();
            player.sendMessage(ChatColor.AQUA + "Set lobby corner 2 location!");
        }
        else if(itemName.equals(SET_SPAWN))
        {
            tempLobby.setSpawn(player.getLocation());
            player.sendMessage(ChatColor.AQUA + "Set lobby spawn location!");
        }
        else if(itemName.equals(CANCEL))
        {
            removeFromSetup(player);
            player.sendMessage(ChatColor.RED + "Setup canceled!");
        }
        else if(itemName.equals(SAVE))
        {
            try {
                Location[] fixedCorners = fixCorners(corner1, corner2);
                tempLobby.setCorner1(fixedCorners[0]);
                tempLobby.setCorner2(fixedCorners[1]);
            } catch(Exception e) {

            }

            if(tempLobby.toLobby() != null)
            {
                player.sendMessage(ChatColor.GREEN+"Saved island!");

                lobbyManager.saveLobby(tempLobby.toLobby());
                removeFromSetup(player);
                event.setCancelled(true);
                return;
            }
            player.sendMessage(ChatColor.RED + "Cannot save island! Make sure you've set everything correctly!");
        }
        event.setCancelled(true);
    }
}
