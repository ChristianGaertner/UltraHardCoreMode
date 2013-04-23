/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Christian
 */
public class ListCommandExecutor implements CommandExecutor {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
//    private Config config;
    
    public ListCommandExecutor(UltraHardCoreMode plugin, Settings settings) {
        this.plugin = plugin;
        this.settings = settings;
//        this.config = config;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        
        Set<String> players = settings.getNames();
        
        
        cs.sendMessage("---------------UHC---------------");
        cs.sendMessage("--------Players Disabled---------");
        
        if (players.isEmpty()) {
            cs.sendMessage("No player is disabled. All playing in UHC");
            return true;
        }
        
        for (String p : players) {
            cs.sendMessage(p);
        }
        
        return true;
    }
    
}
