/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.file;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Christian
 */
public class Config {
    
    private UltraHardCoreMode plugin;
    
    public FileConfiguration config;;
    
    /**
     *
     * @param plugin
     */
    public Config(UltraHardCoreMode plugin){
        this.plugin = plugin;
    }
    
    public void load(){
        this.config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/config.yml"));
    }
    
    public void setDefaultConf(){
        
        
        //alerts (global)
        config.set("alerts.noplayer", "This command can only be run by a player.");
        config.set("alerts.noplayerwithoptions", "This command can only be run by a player or add an option to the command.");
        config.set("alerts.toomanyargs", "Please check your input. Too many arguments.");
        config.set("alerts.noperms", "You do not have the permission to perform this command!");
        config.set("alerts.notonline", "Please choose a player who is online.");
        config.set("alerts.configreset", "The configuration has been resetted.");
        config.set("alerts.banned", "%s died and banned from the server.");
        config.set("alerts.banned-reason", "BANNED: You died on this UltraHardCore Mode enabled server.");
        config.set("alerts.disabled", "You are currently disabled. Please enter UHC in order to be able to issue this command.");
        config.set("alerts.nodisabledplayers", "No player is disabled. All playing in UHC");
        config.set("alerts.nopotions", "Potions do not help you...");
        config.set("alerts.uhcglobalon", "UltraHardCore is globally active on this server.");
        config.set("alerts.uhcglobaloff", "UltraHardCore is globally disabled on this server.");
        config.set("alerts.uhcglobalofferror", "UHC is disabled globally! Use /uhc-toogle global to turn it back on!");
        config.set("alerts.worlddisabled", "UHC is disabled in this world!");
        
        //alerts for /heal
        config.set("alerts.heal.already", "You have full health already!");
        config.set("alerts.heal.full", "You have full health now!");
        config.set("alerts.heal.regain", "You regained %s Hearts!");
        config.set("alerts.heal.noitem", "You do not have enough items...!");
        
        //alerts for /toogle
        config.set("alerts.toogle.disable", "Mode has been toogled! Now the player isn' t in UHC Mode.");
        config.set("alerts.toogle.disableremote", "Mode has been toogled by %s! Now you aren' t in UHC Mode.");
        config.set("alerts.toogle.enable", "Mode has been toogled! Now the player is in UHC Mode.");
        config.set("alerts.toogle.enableremote", "Mode has been toogled by %s! Now you are in UHC Mode.");
        config.set("alerts.heal.noitem", "You do not have enough items...!");
        
        
        //settings
        config.set("settings.regain", 5);
        config.set("settings.ban-on-death", true);
        
//        config.options().copyDefaults(true);
        try {
            config.save(new File(plugin.getDataFolder() + "/config.yml"));
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot save config.");
        }
    }
}
