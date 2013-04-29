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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Christian
 */
public class PlayerJoinListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    private Helper helper;
    
    public PlayerJoinListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        
        Player player = event.getPlayer();
        String world = player.getLocation().getWorld().getName();
        
        if (player.hasPermission("uhc.bypass")) {
            return;
        }
        
        
        if (settings.checkWorldAccess(event.getPlayer().getName(), world)) {
            return;
        }
        
        helper.setTeleportCount(player, 1); //reset data on join!
        
        player.performCommand(config.config.getString("settings.commandforworldchangeplayer"));
        
    }
}
