/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Christian
 */
public class ListCommandExecutor implements CommandExecutor {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    
    public ListCommandExecutor(UltraHardCoreMode plugin, Settings settings, Config config) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        
        if (cs instanceof Player) {
            Player player = (Player) cs;
            if (!player.hasPermission("uhc-list")) {
                player.sendMessage(ChatColor.RED + config.config.getString("alerts.noperms"));
                return true;
            }
        }
        
        Set<String> players = settings.getNames();
        
        if (!settings.globalStatus()) {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.uhcglobalofferror"));
            return true;
        }
        
        cs.sendMessage("---------------UHC---------------");
        cs.sendMessage("--------Players Disabled---------");
        
        if (players.isEmpty()) {
            cs.sendMessage(config.config.getString("alerts.nodisabledplayers"));
            return true;
        }
        
        for (String p : players) {
            cs.sendMessage(p);
        }
        
        return true;
    }
    
}
