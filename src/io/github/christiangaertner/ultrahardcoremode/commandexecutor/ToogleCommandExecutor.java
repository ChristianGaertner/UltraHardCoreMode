/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Christian
 */
public class ToogleCommandExecutor implements CommandExecutor {
 
    private UltraHardCoreMode plugin;
 
    public ToogleCommandExecutor(UltraHardCoreMode plugin) {
            this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (!(cs instanceof Player)) {
            cs.sendMessage("This command can only be run by a player or add flag to the command.");
        } else {
            Player player = (Player) cs;
            player.sendMessage("TOOGLE");
            return true;
        }

        return false;
    }
}
