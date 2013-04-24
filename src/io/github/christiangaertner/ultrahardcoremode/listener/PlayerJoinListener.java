/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Christian
 */
public class PlayerJoinListener implements Listener {
    
    private UltraHardCoreMode plugin;
    private Settings settings;
    private Config config;
    
    public PlayerJoinListener(UltraHardCoreMode plugin, Settings settings, Config config) {
        this.plugin = plugin;
        this.settings = settings;
        this.config = config;
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getName().equals("christian96_g") && Bukkit.getServer().getOnlineMode()) {
            event.setJoinMessage(ChatColor.AQUA + "DaGardner Developer " + ChatColor.GOLD + "christian96_g" + ChatColor.AQUA +" has joined the game!");
        }
    }
}
