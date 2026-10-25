# Oceanraiders - A Minecraft Minigame #

## A minigame plugin for Spigot server in a BungeeCord network ##

This plugin implements Oceanraiders, a game complete with pvp, custom items, GUI shops, and more. Some features include:

* Game world importing and arena setup wizard, with automatic resetting after each game
* Custom items with modified behaviors
* Customizable GUI shops with villager NPCs
* Customizable loot chests
* Player matchmaking provided by the [Spigot-CoreAPI](https://github.com/weijunwang2026/Spigot-CoreAPI) and [Bungee-Core-Plugin](https://github.com/weijunwang2026/Bungee-Core-Plugin)

## Example Commands ##

* /orsetup setup \<island-name\> - Enters setup mode for specified island
* /setstate \<state\> - Sets the current game state

## Dependencies ##

* [Spigot-CoreAPI](https://github.com/weijunwang2026/Spigot-CoreAPI)
* [Party-API-For-Spigot](https://github.com/Simonsator/Party-API-For-Spigot)
* [Friends-API-For-Spigot-For-PAF-Proxy](https://github.com/Simonsator/Friends-API-For-Spigot-For-PAF-Proxy)

## Plugin Installation ##

0. Set up a BungeeCord network with a lobby and a game server where this plugin will be installed. Set up a MySQL database for plugin communication. Set up the Bungee-Core-Plugin along with the dependencies using the links above.
1. Place this plugin in the plugins folder and start the server. This will create the plugin folder and prompt you to import a world.
2. Import your world folder into OceanRaiders-1.21/gamemaps, and name the folder "oceanraiders"
3. Set up the serverid for this server in the Bungee and Spigot core plugins (tutorial in the links above)
4. Use /join oceanraiders to join the game arena, which we will configure here (Note any changes made to this world disappears once the server restarts)
5. Run /orsetup setup Blue Island to configure blue team's island. Set the locations around your world using the items in your inventory and save the island at the end. Do this for both islands.
6. Run /orsetup lobby to configure the waiting lobby (the waiting lobby is removed upon game start)
7. Test your arena by running /setstate active to force-start the game. Normally, the game starts automatically when at least two players join.

## Config Files ##

The config files for this plugin enables customization:
* armors - items for the armor shop
* bows - items for the archery shop
* customitems - display-items for custom items
* enchants - available enchants for each armoury
* food - items for the food shop
* genericloot - loot table for loot scattered around the arena
* islands - saves arena configurations
* lobby - saves waiting lobby configurations
* loot - loot table for the three loot chests on each team
* potion - items for the potion shop
* splash_potion - items for the splash potion shop
* swords - items for the swords shop
* transferreditems - items transferred to the player that kill another player
  
