/*
 *
 *
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Christian
 */
public class PlayerDeathListener implements Listener{
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    
    public PlayerDeathListener(UltraHardCoreMode plugin, Settings settings, Config config) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
    }
    
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        
        if (!settings.globalStatus()) {
            return;
        }
        
        Player player = event.getEntity().getPlayer();
        
        
        if (config.config.getBoolean("settings.permit-world-access-on-death")) {
            settings.banFromWorld(player.getName(), player.getLocation().getWorld().getName());
        }
        
        if (!config.config.getBoolean("settings.ban-on-death")) {
            return;
        }
           
        
        player.setBanned(true);

        plugin.getServer().broadcastMessage(ChatColor.GOLD + "[UHC]" + ChatColor.RED + String.format(config.config.getString("alerts.banned"), player.getName()));
        
        player.kickPlayer(config.config.getString("alerts.banned-reason"));
    }
    
}
