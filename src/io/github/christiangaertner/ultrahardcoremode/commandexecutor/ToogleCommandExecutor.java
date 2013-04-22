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
                cs.sendMessage(ChatColor.RED + "This command can only be run by a player or add an option to the command.");
                return false;
            } else {
                Player player = (Player) cs;
                
                if (!player.hasPermission("uhc.toogle.*") || !player.hasPermission("uhc.toogle.self") || !player.hasPermission("uhc.toogle.remote")) {
                    player.sendMessage(ChatColor.RED + "You do not have the permission to perform this command.");
                    return true;
                }
                
                
                settings.setStatus(player, !settings.isDisabled(player));
                return true;
            } 
        } else if (strings.length == 1) {
            Player player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage(ChatColor.LIGHT_PURPLE + "Please choose a player who is online.");
                return false;
            }
            
            if (cs instanceof Player) {
                Player sender = (Player) cs;
                if (!sender.hasPermission("uhc.toogle.*") || !sender.hasPermission("uhc.toogle.remote")) {
                    sender.sendMessage(ChatColor.RED + "You do not have the permission to perform this command.");
                    return true;
                }    
            }
            
            
            
            
            settings.setStatus(player, !settings.isDisabled(player));
            
            if (settings.isDisabled(player)) {
                cs.sendMessage(ChatColor.GREEN + "Mode has been toogled! Now you aren' t in UHC Mode.");
                player.sendMessage(ChatColor.RED + "Mode has been toogled by " + cs.getName() + "! Now you are in UHC Mode.");
            } else {
                cs.sendMessage(ChatColor.RED + "Mode has been toogled! Now you are in UHC Mode.");
                player.sendMessage(ChatColor.RED + "Mode has been toogled by " + cs.getName() + "! Now you are in UHC Mode.");
            }
            
            return true;
            
        } else {
            cs.sendMessage(ChatColor.RED + "Please check your input. Too many arguments.");
            return false;
        }
        

    }
}
