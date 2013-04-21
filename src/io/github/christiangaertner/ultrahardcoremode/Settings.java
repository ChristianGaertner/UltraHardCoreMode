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
    
    private Set<Player> primary = new HashSet<Player>();
    
    public boolean isDisabled(Player player) {
        return primary.contains(player);
    }
    
    public void setStatus(Player player, boolean enabled) {
        if (enabled) {
            primary.add(player);
        } else {
            primary.remove(player);
        }
    }
    
}
