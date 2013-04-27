/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author Christian
 */
public class PlayerTeleportListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    
    public PlayerTeleportListener(UltraHardCoreMode plugin, Settings settings, Config config) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        
        Location dest = event.getTo();
        String destWorld = dest.getWorld().getName();
        
        if (settings.checkWorldAccess(event.getPlayer().getName(), destWorld)) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        
    }
}
