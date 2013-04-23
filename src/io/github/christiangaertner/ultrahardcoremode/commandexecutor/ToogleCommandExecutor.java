/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Christian
 */
public class ToogleCommandExecutor implements CommandExecutor {
 
    private UltraHardCoreMode   plugin;
    private Settings            settings;
    private Config              config;
   
 
    public ToogleCommandExecutor(UltraHardCoreMode plugin, Settings settings, Config config) {
            this.plugin = plugin;
            this.settings = settings;
            this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (strings.length == 0) {
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.RED + config.config.getString("alerts.noplayerwithoptions"));
                return false;
            } else {
                Player player = (Player) cs;
                
                if (!player.hasPermission("uhc.toogle.*") || !player.hasPermission("uhc.toogle.self") || !player.hasPermission("uhc.toogle.remote")) {
                    player.sendMessage(ChatColor.RED + config.config.getString("alerts.noperms"));
                    return true;
                }
                
                
                settings.setStatus(player, !settings.isDisabled(player));
                return true;
            } 
        } else if (strings.length == 1) {
            Player player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage(ChatColor.LIGHT_PURPLE + config.config.getString("alerts.notonline"));
                return false;
            }
            
            if (cs instanceof Player) {
                Player sender = (Player) cs;
                if (!sender.hasPermission("uhc.toogle.*") || !sender.hasPermission("uhc.toogle.remote")) {
                    sender.sendMessage(ChatColor.RED + config.config.getString("alerts.noperms"));
                    return true;
                }    
            }
            
            
            
            
            settings.setStatus(player, !settings.isDisabled(player));
            
            if (settings.isDisabled(player)) {
                cs.sendMessage(ChatColor.GREEN + config.config.getString("alerts.toogle.disable"));
                player.sendMessage(ChatColor.GREEN + String.format(config.config.getString("alerts.toogle.disableremote"), cs.getName()));
            } else {
                cs.sendMessage(ChatColor.RED + config.config.getString("alerts.toogle.enable"));
                player.sendMessage(ChatColor.RED + String.format(config.config.getString("alerts.toogle.enableremote"), cs.getName()));
            }
            
            return true;
            
        } else {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.toomanyargs"));
            return false;
        }
        

    }
}
