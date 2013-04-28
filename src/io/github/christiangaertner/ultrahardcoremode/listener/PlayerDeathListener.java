/*
 *
 *
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
import org.bukkit.event.entity.PlayerDeathEvent;


/**
 *
 * @author Christian
 */
public class PlayerDeathListener implements Listener{
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    private Helper helper;
    
    public PlayerDeathListener(UltraHardCoreMode plugin, Settings settings, Config config, Helper helper) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
        this.helper = helper;
    }
    
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        
        Player player;
        
        if (!settings.globalStatus()) {
            return;
        }
        
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            return;
        }
        
        
        
        if (player.hasPermission("uhc.bypass")) {
            return;
        }
        
        
        helper.setDeathStatus(player, true);
        
        
        
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
