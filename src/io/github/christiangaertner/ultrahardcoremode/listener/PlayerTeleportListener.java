/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Helper;
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
    private Helper helper;
    
    public PlayerTeleportListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        
        if (event.getPlayer().hasPermission("uhc.bypass")) {
            return;
        }
        
        
        String destWorld = event.getTo().getWorld().getName();
        
        if (settings.checkWorldAccess(event.getPlayer().getName(), destWorld)) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        
        if (helper.getTeleportCount(event.getPlayer()) == 1) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), String.format(config.config.getString("settings.commandforworldchangeconsole"), event.getPlayer().getName()));
            helper.setTeleportCount(event.getPlayer(), 2);
            if (!event.isCancelled()) {
                event.setCancelled(true);
            }
        } else if (helper.getTeleportCount(event.getPlayer()) == 2) {
            helper.setTeleportCount(event.getPlayer(), 0);
            event.getPlayer().kickPlayer(config.config.getString("alerts.banned-reason"));
            if (!event.isCancelled()) {
                event.setCancelled(true);
            }
        }
        
        event.getPlayer().sendMessage(config.config.getString("alerts.noteleport"));
        event.setCancelled(true);
        
    }
}
