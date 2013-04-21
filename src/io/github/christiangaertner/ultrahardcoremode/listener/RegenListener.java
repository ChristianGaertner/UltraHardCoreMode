/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.listener;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 *
 * @author Christian
 */
public class RegenListener implements Listener {
    
    private final Settings settings;
    
    public RegenListener(Settings settings) {
        this.settings = settings;
    }

    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        
        if(!(event.getEntity() instanceof Player)) {
            return; //we only want to affect players
        }
        
        //get Player
        Player player = (Player) event.getEntity();
        
        if (player.hasPermission("uhc.bypass")) {
            return;
        }
        
        if (settings.isDisabled(player)) {
            return;
        }
        
        
        
        event.setCancelled(true);
    }
}
