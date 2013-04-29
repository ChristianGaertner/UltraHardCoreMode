/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Helper;
import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Christian
 */
public class PlayerRespawnListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    private Helper helper;
    
    public PlayerRespawnListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }
    
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        
        Player player = event.getPlayer();
        
        if (player.hasPermission("uhc.bypass")) {
            return;
        }
        
        if (!helper.getDeathStatus(player)) {
            return;
        }
        
        
        if (settings.checkWorldAccess(player.getName(), player.getLocation().getWorld().getName())) {
            return;
        }
        
        
        helper.setTeleportCount(player, 1);
        Bukkit.dispatchCommand(player, config.config.getString("settings.commandforworldchangeplayer"));
        
    }
    
}
