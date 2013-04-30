/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Christian
 */
public class PardonCommandExecutor implements CommandExecutor {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    
    public PardonCommandExecutor(UltraHardCoreMode plugin, Settings settings, Config config) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        if (strings.length == 0) {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.notenoughargs"));
            return false;
        }
        
        if (strings.length == 1) {
            
            if (settings.userIsBanned(strings[0], null)) {

                HashSet<String> worlds = settings.getBannedWorlds().get(strings[0]);

                
                for (String w : worlds) {
                    settings.unbanFromWorld(strings[0], w);
                }
                
                cs.sendMessage(ChatColor.GREEN + String.format(config.config.getString("alerts.pardon.sucessall"), worlds.size(), strings[0]));
                return true;


            } else {
                cs.sendMessage(ChatColor.RED + config.config.getString("alerts.pardon.notbannedall"));
                return true;
            }
                        
        }
        
        if (strings.length == 2) {
            
            if (settings.userIsBanned(strings[0], strings[1])) {
               
               settings.unbanFromWorld(strings[0], strings[1]);
               cs.sendMessage(ChatColor.GREEN + String.format(config.config.getString("alerts.pardon.sucessone"), strings[0], strings[1]));
               return true; 
            } else {
                cs.sendMessage(ChatColor.RED + config.config.getString("alerts.pardon.notbannedone"));
                return true;
            }
        }
        
        if (strings.length > 2) {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.toomanyargs"));
            return false;
        }
        
        return false;
    }
}
