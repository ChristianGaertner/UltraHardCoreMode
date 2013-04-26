/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

/**
 *
 * @author Christian
 */
public class Settings {
    
    private Set<String> primary = new HashSet<String>();
    private Set<String> worlds = new HashSet<String>();
    private boolean world_whitelist;
    private boolean enabled;

    public Settings() {
        this.enabled = true;
        this.world_whitelist = false;
    }
    
    //DISABLE/ENABLE PLAYER START---
    public boolean isDisabled(Player player) {
        String playerName = player.getName();
        return primary.contains(playerName);
    }
    
    public void setStatus(Player player, boolean enabled) {
        String playerName = player.getName();

        if (enabled) {
            primary.add(playerName);
        } else {
            primary.remove(playerName);
        }
    }
    
    public Set<String> getNames(){
        return primary;
    }
    
    public void initHashSetPlayers(Set<String> newSet) {
        this.primary = newSet;
    }
    //DISABLE/ENABLE PLAYER END---
    
    //DISABLE/ENABLE GLOBAL START---
    
    public void setGlobalStatus(boolean newStatus) {
        enabled = newStatus;
    }
    
    public boolean globalStatus(){
        return enabled;
    }
    //DISABLE/ENABLE GLOBAL END---
    
    //MULTIWORLD STUFF START---
    
    public boolean checkWorld(String world) {
        if (world_whitelist) {
           if (worlds.contains(world)) {
            return true;
            }
        
            return false;
            
        } else {
            if (worlds.contains(world)) {
                return false;
            }
            return true;
        }

    }
    
    public void initHashSetWorlds(Set<String> newSet) {
        this.worlds = newSet;
    }
    
    public void initWorldListMode(boolean whitelist) {
        this.world_whitelist = whitelist;
    }
    
    //MULTIWORLD STUFF END---
    
}
