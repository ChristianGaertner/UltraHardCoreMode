UltraHardCoreModeBukkit
========================

[View on dev.Bukkit.org]("http://dev.bukkit.org/server-mods/ultrahardcore-mode/")

UltraHardCore Mode gained on popularity in the last few months really quickly. It is basically vanilla hardcore mode but harder. UltraHardCore Mode facts

No health regeneration
Potions won' t work either
Only way to regain health is by consuming a "Golden Apple"
Ban on death
A few weeks ago SethBling introduced his UHC-Box on YouTube. This box does all of this features more or less.

But since this is vanilla Minecraft there are minor issues:

Potion effects to prevent regeneration (really annoying!)
Items are getting removed from the inventory instantly!
User does not get banned, but "jailed" in the air
I thought, that this is a great idea and with the possibilities of CraftBukkit really easy to develop. This plugin fixes all these "bugs" and adds a few improvements. The basic idea and gameplay is still the same

Features of this plugin
========================
* Every output by the plugin is configurable thru the config.yml
* Toogle plugin on or off for yourself or any other plugin
* List disabled plugins
* Heal only by executing command which drains 1 gold block and 1 apple from the players inventory
* Amount of healing configurable
* Dis-/enable "Ban on Death"
* Disabled players are stored in easy editable flatfile database (YAML)
* Permissions for bypassing the plugin
* Switch plugin on or off with simple command (or via config)

Commands
========================
* uhc-heal
	* (Heals you by certain amount of hearts, requires 1 apple and gold block)
* uhc-toogle [option:player|global]
	* (Toogles UHC on or off for the command executor or the player attached)
* uhc-list
	* (List all disabled players)
* uhc-resetconfig
	* (Resets the config to default)


Permissions
========================
* uhc.*
	* (Give access to all commands)
* uhc.toogle.self
	* (Allows the user to toogle UHC on or off)
* uhc.toogle.remote 
	* (Allows the user to toogle UHC on or off for both them and others)
* uhc.bypass
	* (Bypasses the UHC Plugin)
* uhc.denyheal
	* (Permits the usage of the command /heal)
* uhc.list
	* (Allows to list every disabled player)
* uhc.resetconfig
	* (Allows to reset the config)

TODO
========================
* Add option to configure items needed for /heal
* Make a global on/off switch **DONE**
* We' ll see :D

OpenSource
========================
This project is opensource! UltraHardCoreMode on GitHub