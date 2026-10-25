# Oceanraiders - A Minecraft Minigame #

## A minigame plugin for Spigot server in a Bungeecord network ##

This plugin implements OceanRaiders, a game complete with pvp, custom items, GUI shops, and more. Some features include:

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

0. Create and setup a Bungeecord network with a lobby and a game server where this plugin will be installed. Create a MySQL database for plugin communication. Setup the Bungee-Core-Plugin along with the dependencies using the links above.
1. Place this plugin in the plugins folder on the game server and start it. This will create the plugin folder.
2. Import your game world into OceanRaiders-1.21/gamemaps, and name it "oceanraiders"
3. Setup up the server id for this server for the core plugins (tutorial in the links above)
4. Use /join oceanraiders to join the game arena, which we will use to set up (Note any changes you make to this world will disappear once the server restarts)
5. Run /orsetup setup Blue Island to setup the blue island. Use the items to set the locations, and save the island at the end. Do this for both islands.
6. Run /orsetup lobby to setup the waiting lobby (where players wait for the countdown)
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
  
