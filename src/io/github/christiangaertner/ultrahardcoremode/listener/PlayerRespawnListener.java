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
import org.bukkit.Location;
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
        
        
        if (settings.checkWorldAccess(player.getName(), event.getRespawnLocation().getWorld().getName())) {
            return;
        }
        
        
        String world = config.config.getString("settings.tp.world");
        Double x = config.config.getDouble("settings.tp.x");
        Double y = config.config.getDouble("settings.tp.y");
        Double z = config.config.getDouble("settings.tp.z");
        
        Location newLoc = new Location(Bukkit.getWorld(world), x, y, z);
        
        if (settings.checkWorldAccess(player.getName(), world)) {
            event.setRespawnLocation(newLoc);
            return;
        }
        
        player.kickPlayer(config.config.getString("alerts.banned-reason"));
        
        
    }
    
}
