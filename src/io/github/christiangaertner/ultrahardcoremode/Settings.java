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
    private boolean enabled;

    public Settings() {
        this.enabled = true;
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
    
    public void initHashSet(Set<String> newSet) {
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
    
}
