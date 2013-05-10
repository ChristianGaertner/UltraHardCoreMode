/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.file;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Christian
 */
public class FlatFileDataBase {
    
    private UltraHardCoreMode plugin;
    private Config config;
    private FileConfiguration fc;
    /**
     * Just checks if this is the initial start of the plugin, for checking if we should try to load a database file
     */
    public boolean initialStart = false;
    
    public FlatFileDataBase(UltraHardCoreMode plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }
    
    public void initDataBase(){
        
        //load config
        config.load();
        
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            initialStart = true;
        }
        
        if (!(new File(plugin.getDataFolder(), "/config.yml")).exists()) {
            config.setDefaultConf();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data")).exists()) {
            (new File(plugin.getDataFolder(), "/data")).mkdir();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled")).exists()) {
            (new File(plugin.getDataFolder(), "/data/disabled")).mkdir();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/dastats.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/dastats.yml")).createNewFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile (GUID ERR).");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/worlds.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/worlds.yml")).createNewFile();
                seedWorldsFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/bannedworlds.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/bannedworlds.yml")).createNewFile();
                
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/disabled/players.yml")).createNewFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/global.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/disabled/global.yml")).createNewFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        //just for saftey
        plugin.reloadConfig();
        
        
    }
    
    private void seedWorldsFile() {
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/worlds.yml")).exists()) {
            return;
        }
        
        try {
            
            fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/worlds.yml"));
            
            
            List<String> worlds = new ArrayList<String>();
            
            worlds.add("world_name_here");
            
            fc.set("whitelist", false);
            fc.set("worlds", worlds);
            fc.save(new File(plugin.getDataFolder() + "/worlds.yml"));
            
            
        } catch (FileNotFoundException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot find databasefile.");
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot open databasefile.");
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    public Set<String> loadPlayersDisabled(){      
        Set<String> players;
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));

        List<String> list = (List<String>) fc.getList("players-disabled");
        
        players = new HashSet(list);
        
        
        
        return players;
    }
    
    public void savePlayersDisabled(Set<String> players){
        
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.yml")).exists()) {
            return;
        }
        
        try {
            
            fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));
            
            
            List<String> list = new ArrayList<String>();
            
            for (String p : players) {
                list.add(p);
            }
            
            fc.set("players-disabled", list);
            fc.save(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));
            
            
        } catch (FileNotFoundException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot find databasefile.");
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot open databasefile.");
        }
        
    }
    

    public boolean loadGlobalStatus(){      
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));

        
        boolean status = fc.getBoolean("global");
        
        status = !status;
        
        return status;
    }
    
    public void saveGlobalStatus(boolean status){
        
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/data/disabled/global.yml")).exists()) {
            return;
        }
        
        try {
            
            fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));
            
            
            
            fc.set("global", !status);
            fc.save(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));
            
            
        } catch (FileNotFoundException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot find databasefile.");
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot open databasefile.");
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public Set<String> loadWorlds() {
        
        Set<String> worlds;
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/worlds.yml"));
        
        List<String> list = (List<String>) fc.getList("worlds");
        
        worlds = new HashSet(list);
        
        return worlds;
    }
    
    public boolean loadWorldMode() {
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/worlds.yml"));
        
        boolean whitelist = fc.getBoolean("whitelist");
        return whitelist;
    }
    
    public void saveBannedWorlds(HashMap<String, HashSet<String>> bannedWorlds) {
        
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/data/bannedWorlds.yml")).exists()) {
            return;
        }
        
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/bannedWorlds.yml"));
        
        for (Entry<String, HashSet<String>> entry : bannedWorlds.entrySet()) {
            String player = entry.getKey();
            HashSet<String> worlds = entry.getValue();
            
            List<String> list = new ArrayList<String>();
            
            for (String p : worlds) {
                list.add(p);
            }
            
            fc.set(player, list);
            
        }
        try {
            fc.save(new File(plugin.getDataFolder() + "/data/bannedWorlds.yml"));
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot open databasefile.");
        }
        
        
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, HashSet<String>> loadBannedWorlds() {
        
        HashMap<String, HashSet<String>> bannedWorlds;
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/bannedWorlds.yml"));
        
        bannedWorlds = new HashMap<String, HashSet<String>>();
        
        for (String player : fc.getKeys(true)) {
            
            List<String> worldsTMP = new ArrayList<String>();
            
            for (String world : fc.getStringList(player)) {
                worldsTMP.add(world);
            }
            
            Set<String> worldsTMP2 = new HashSet<String>(worldsTMP);
            
            bannedWorlds.put(player, (HashSet<String>) worldsTMP2);
        }
        
        return bannedWorlds;
    }
    
    
    public void saveDaStats(String guid, Boolean optout) {
        
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/dastats.yml"));
        
        fc.set("opt-out", optout);
        fc.set("guid", guid);
        
        
        try {
            fc.save(new File(plugin.getDataFolder() + "/data/dastats.yml"));
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Cannot open databasefile (GUID ERR).");
        }
        
    }
    
    public String loadDaStats(String which) {
        
        
        
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/dastats.yml"));
        
        if (which.equalsIgnoreCase("guid")) return fc.getString("guid");
        if (which.equalsIgnoreCase("opt-out") && fc.getBoolean("opt-out")) {
            return "true";
        }else if (which.equalsIgnoreCase("opt-out")) {
            return "false";
        }
        return null;
    }
    
    
}
