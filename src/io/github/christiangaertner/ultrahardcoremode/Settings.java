/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import java.util.HashMap;
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
    private HashMap <String, HashSet<String>> bannedWorlds = new HashMap <String, HashSet<String>>();
    private boolean world_whitelist;
    private boolean enabled;
    private boolean potions;

    public Settings() {
        this.enabled = true;
        this.world_whitelist = false;
    }
    
    public boolean potions() {
        return this.potions;
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
    
    public boolean userIsBanned(String player, String world) {
        Set<String> worldsTMP = bannedWorlds.get(player);
        
        if (worldsTMP == null) {
            return false; //that means, that the player is not banned from any world
        }
        
        if (world != null) {
            if (worldsTMP.contains(world)) {
                return true;
            }
        }
        
        return true; //if worldsTMP is not null, the player has to be banned in some world
    }
    
    /**
     * Bans the given player from the given world
     * @param player
     * @param worlds
     */
    public void banFromWorld(String player, String world) {
        Set<String> worldsTMP = bannedWorlds.get(player);
        
        if (worldsTMP == null) {
            worldsTMP = new HashSet<String>();
        }

        worldsTMP.add(world);
        bannedWorlds.put(player, (HashSet<String>) worldsTMP);
    }
    
    /**
     * Unbans the given player from the given world; if the players is banned in one world only,
     * the complete entry will be removed.
     * @param player
     * @param world
     */
    public void unbanFromWorld(String player, String world) {
        Set<String> worldsTMP = bannedWorlds.get(player);
        
        if (worldsTMP == null) {
            return;
        }
        
        worldsTMP.remove(world);
        if (worldsTMP.isEmpty()) {
            bannedWorlds.remove(player);
            return;
        }
        bannedWorlds.put(player, (HashSet<String>) worldsTMP);
    }
    
    /**
     * Checks if a player is allowed to enter the given world world
     * returns true if the player is allowed to enter the given world
     * @param player
     * @param world
     * @return
     */
    public boolean checkWorldAccess(String player, String world) {
        
        Set<String> worldsTMP = bannedWorlds.get(player);
        
        if (worldsTMP == null) {
            return true; //that means, that the player is not banned from any world
        }
        
        if (worldsTMP.contains(world)) {
            return false;
        }
        
        return true;
    }
        
    //MULTIWORLD STUFF END---
    
    
    //INIT
    public void initHashSetPlayers(Set<String> newSet) {
        this.primary = newSet;
    }
    public void initHashSetWorlds(Set<String> newSet) {
        this.worlds = newSet;
    }
    
    public void initWorldListMode(boolean whitelist) {
        this.world_whitelist = whitelist;
    }

    public void initBannedWorlds(HashMap<String, HashSet<String>> newMap) {
        this.bannedWorlds = newMap;
    }
    
    public void initPotions(boolean potions) {
        this.potions = potions;
    }
    
    //SAVE
    public Set<String> getNames() {
        return primary;
    }
    
    public HashMap<String, HashSet<String>> getBannedWorlds() {
        return bannedWorlds;
    }
    
}
