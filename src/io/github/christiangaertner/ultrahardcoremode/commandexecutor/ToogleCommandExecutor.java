/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
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
   
 
    public ToogleCommandExecutor(UltraHardCoreMode plugin, Settings settings) {
            this.plugin = plugin;
            this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (strings.length == 0) {
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.RED + plugin.getConfig().getString("config.alerts.noplayerwithoptions"));
                return false;
            } else {
                Player player = (Player) cs;
                
                if (!player.hasPermission("uhc.toogle.*") || !player.hasPermission("uhc.toogle.self") || !player.hasPermission("uhc.toogle.remote")) {
                    player.sendMessage(ChatColor.RED + plugin.getConfig().getString("config.alerts.noperms"));
                    return true;
                }
                
                
                settings.setStatus(player, !settings.isDisabled(player));
                return true;
            } 
        } else if (strings.length == 1) {
            Player player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage(ChatColor.LIGHT_PURPLE + plugin.getConfig().getString("config.alerts.notonline"));
                return false;
            }
            
            if (cs instanceof Player) {
                Player sender = (Player) cs;
                if (!sender.hasPermission("uhc.toogle.*") || !sender.hasPermission("uhc.toogle.remote")) {
                    sender.sendMessage(ChatColor.RED + plugin.getConfig().getString("config.alerts.noperms"));
                    return true;
                }    
            }
            
            
            
            
            settings.setStatus(player, !settings.isDisabled(player));
            
            if (settings.isDisabled(player)) {
                cs.sendMessage(ChatColor.GREEN + plugin.getConfig().getString("config.alerts.toogle.disable"));
                player.sendMessage(ChatColor.GREEN + String.format(plugin.getConfig().getString("config.alerts.toogle.disableremote"), cs.getName()));
            } else {
                cs.sendMessage(ChatColor.RED + plugin.getConfig().getString("config.alerts.toogle.enable"));
                player.sendMessage(ChatColor.RED + String.format(plugin.getConfig().getString("config.alerts.toogle.enableremote"), cs.getName()));
            }
            
            return true;
            
        } else {
            cs.sendMessage(ChatColor.RED + plugin.getConfig().getString("config.alerts.toomanyargs"));
            return false;
        }
        

    }
}
