/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Helper;
import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

/**
 *
 * @author Christian
 */
public class PlayerPortalListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    private Helper helper;
    
    public PlayerPortalListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPortal(PlayerPortalEvent event) {
        
        Player player = event.getPlayer();
        
        String destWorld = event.getTo().getWorld().getName();
        
        plugin.getServer().broadcastMessage("1: " + destWorld);
        
        if (settings.checkWorldAccess(player.getName(), destWorld)) {
            plugin.getServer().broadcastMessage("2: " + destWorld);
            return;
        }
        
        plugin.getServer().broadcastMessage("3: " + destWorld);
        
        if (event.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        
    }
}
