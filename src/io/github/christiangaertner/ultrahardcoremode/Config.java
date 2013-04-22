/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Christian
 */
public class Config {
    
    private UltraHardCoreMode plugin;
    
    public Config(UltraHardCoreMode plugin){
        this.plugin = plugin;
    }
    
    public void setDefaults(){
        final FileConfiguration config = plugin.getConfig();
        
        
        //alerts (global)
        config.addDefault("config.alerts.noplayer", "This command can only be run by a player.");
        config.addDefault("config.alerts.noplayer.withoptions", "This command can only be run by a player or add an option to the command.");
        config.addDefault("config.alerts.toomanyargs", "Please check your input. Too many arguments.");
        config.addDefault("config.alerts.noperms", "You do not have the permission to perform this command!");
        config.addDefault("config.alerts.notonline", "Please choose a player who is online.");
        config.addDefault("config.alerts.disabled", "You are currently disabled. Please enter UHC in order to be able to issue this command.");
        
        //alerts for /heal
        config.addDefault("config.alerts.heal.already", "You have full health already!");
        config.addDefault("config.alerts.heal.full", "You have full health now!");
        config.addDefault("config.alerts.heal.regain", "You regained %s Hearts!");
        config.addDefault("config.alerts.heal.noitem", "You do not have enough items...!");
        
        //alerts for /toogle
        config.addDefault("config.alerts.toogle.disable", "Mode has been toogled! Now you aren' t in UHC Mode.");
        config.addDefault("config.alerts.toogle.disableremote", "Mode has been toogled by %s! Now you are in UHC Mode.");
        config.addDefault("config.alerts.toogle.enable", "Mode has been toogled! Now you are in UHC Mode.");
        config.addDefault("config.alerts.toogle.enableremote", "Mode has been toogled by %s! Now you are in UHC Mode.");
        config.addDefault("config.alerts.heal.noitem", "You do not have enough items...!");
        
        
        
        //settings
        config.addDefault("config.settings.regain", 5);
        
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
