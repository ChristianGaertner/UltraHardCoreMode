/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Helper;
import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

/**
 *
 * @author Christian
 */
public class RegainListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    private Helper helper;
    
    public RegainListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }

    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        
        
        if(!(event.getEntity() instanceof Player)) {
            return; //we only want to affect players
        }
        
        //get Player
        Player player = (Player) event.getEntity();
        
        if (!plugin.checkExec(player, player.getWorld())) {
            return;
        }
        
        if (event.getRegainReason() == RegainReason.MAGIC || event.getRegainReason() == RegainReason.MAGIC_REGEN) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + config.config.getString("alerts.nopotions"));
        }
        
        
        if (event.isCancelled()) {
            return;
        }
        event.setCancelled(true);
    }
}
