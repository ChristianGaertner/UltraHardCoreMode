/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import org.bukkit.Bukkit;
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
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (strings.length == 0) {
            if (!(cs instanceof Player)) {
                cs.sendMessage("This command can only be run by a player or add an option to the command.");
                return false;
            } else {
                Player player = (Player) cs;
                settings.setStatus(player, !settings.isDisabled(player));
                return true;
            } 
        } else if (strings.length == 1) {
            Player player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage("Please add a player who is online.");
                return false;
            }
            
            settings.setStatus(player, !settings.isDisabled(player));
            return true;
            
        } else {
            cs.sendMessage("Please check your input. Too many arguments.");
            return false;
        }
        

    }
}
