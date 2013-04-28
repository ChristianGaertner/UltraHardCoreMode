/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author Christian
 */
public class Helper {
    
    private UltraHardCoreMode plugin;
    
    public Helper(UltraHardCoreMode plugin) {
        this.plugin = plugin;
    }
    
    public boolean getDeathStatus(Player player) {
        
        List<MetadataValue> values = player.getMetadata("UHC-DeathStatus");
        for(MetadataValue val : values) {
            if(val.getOwningPlugin().getName().equals(plugin.getName())) {
                return val.asBoolean();
            }
        }
        
        return false;
    }
    
    public void setDeathStatus(Player player, boolean status) {
        player.setMetadata("UHC-DeathStatus", new FixedMetadataValue(plugin, status));
    }
    
    
    public int getTeleportCount(Player player) {
        
        List<MetadataValue> values = player.getMetadata("UHC-Teleport-Count");
        for(MetadataValue val : values) {
            if(val.getOwningPlugin().getName().equals(plugin.getName())) {
                return val.asInt();
            }
        }
        
        return 0;
    }
    
    public void setTeleportCount(Player player, int count){
        player.setMetadata("UHC-Teleport-Count", new FixedMetadataValue(plugin, count));
    }
}
