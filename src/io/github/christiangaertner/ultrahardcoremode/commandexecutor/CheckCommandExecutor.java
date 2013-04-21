/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

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
public class CheckCommandExecutor implements CommandExecutor{
    
    private UltraHardCoreMode plugin;
 
    public CheckCommandExecutor(UltraHardCoreMode plugin) {
            this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (strings.length == 0) {
            if (!(cs instanceof Player)) {
                 cs.sendMessage("This command can only be run by a player or add a player as argument.");
                return false;
            }
            
            Player player = (Player) cs;
            
            
            player.sendMessage(player.getName() + ", this feature is coming soon!");

            return true;
            
        } else if (strings.length == 1) {
            
            Player player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage("User not online");
            }
            cs.sendMessage(cs.getName() + ", this feature is coming soon!");
        }
        
        return false;
    }
        
}
