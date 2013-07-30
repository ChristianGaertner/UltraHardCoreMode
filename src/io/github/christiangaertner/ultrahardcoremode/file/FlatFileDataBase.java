/*
 *
 *
 */
package io.github.christiangaertner.ultrahardcoremode.file;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
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
    
    public void initDataBase() throws IOException{
        
        //load config
        config.load();
        
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            this.initialStart = true;
        }
        
        if (!(new File(plugin.getDataFolder(), "/config.yml")).exists()) {
            config.setDefaultConf();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data")).exists()) {
            File data = new File(plugin.getDataFolder(), "/data");
            data.mkdir();
            data.setReadable(true);
            data.setWritable(true);
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled")).exists()) {
            File disabled = new File(plugin.getDataFolder(), "/data/disabled");
            disabled.mkdir();
            disabled.setReadable(true);
            disabled.setWritable(true);
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/dastats.yml")).exists()) {
            
            File dastats = new File(plugin.getDataFolder(), "/data/dastats.yml");
            dastats.createNewFile();
            dastats.setReadable(true);
            dastats.setWritable(true);
            if (!dastats.canWrite()) {
                throw new IOException("File is not writable.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/worlds.yml")).exists()) {

            File worlds = new File(plugin.getDataFolder(), "/worlds.yml");
            worlds.createNewFile();
            worlds.setReadable(true);
            worlds.setWritable(true);
            if (!worlds.canWrite()) {
                throw new IOException("File is not writable.");
            }
            seedWorldsFile();
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/bannedworlds.yml")).exists()) {
            
            File bannedworlds = new File(plugin.getDataFolder(), "/data/bannedworlds.yml");
            bannedworlds.createNewFile();
            bannedworlds.setReadable(true);
            bannedworlds.setWritable(true);
            if (!bannedworlds.canWrite()) {
                throw new IOException("File is not writable.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.yml")).exists()) {
            
            File playersDisabled = new File(plugin.getDataFolder(), "/data/disabled/players.yml");
            playersDisabled.createNewFile();
            playersDisabled.setReadable(true);
            playersDisabled.setWritable(true);
            if (!playersDisabled.canWrite()) {
                throw new IOException("File is not writable.");
            }
            
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/global.yml")).exists()) {
            
            File globalDisabled = new File(plugin.getDataFolder(), "/data/disabled/global.yml");
            globalDisabled.createNewFile();
            globalDisabled.setReadable(true);
            globalDisabled.setWritable(true);
            if (!globalDisabled.canWrite()) {
                throw new IOException("File is not writable.");
            }
            
        }
        
        //just for saftey
        plugin.reloadConfig();
        
        
    }
    
    private void seedWorldsFile() throws IOException {
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/worlds.yml")).exists()) {
            return;
        }
        
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/worlds.yml"));


        List<String> worlds = new ArrayList<String>();

        worlds.add("world_name_here");

        fc.set("whitelist", false);
        fc.set("worlds", worlds);
        fc.save(new File(plugin.getDataFolder() + "/worlds.yml"));       
        
    }
    
    @SuppressWarnings("unchecked")
    public Set<String> loadPlayersDisabled(){      
        Set<String> players;
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));

        List<String> list = (List<String>) fc.getList("players-disabled");
        
        players = new HashSet(list);
        
        
        
        return players;
    }
    
    public void savePlayersDisabled(Set<String> players) throws IOException{
        
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.yml")).exists()) {
            return;
        }
                  
            fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));
            
            
            List<String> list = new ArrayList<String>();
            
            for (String p : players) {
                list.add(p);
            }
            
            fc.set("players-disabled", list);
            fc.save(new File(plugin.getDataFolder() + "/data/disabled/players.yml"));

        
    }
    

    public boolean loadGlobalStatus(){      
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));

        
        boolean status = fc.getBoolean("global");
        
        status = !status;
        
        return status;
    }
    
    public void saveGlobalStatus(boolean status) throws IOException{
        
        //if no folder exists, the plugin may be deleted.
        if (!(new File(plugin.getDataFolder(), "/data/disabled/global.yml")).exists()) {
            return;
        }
        
            
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));



        fc.set("global", !status);
        fc.save(new File(plugin.getDataFolder() + "/data/disabled/global.yml"));
        
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
    
    public void saveBannedWorlds(HashMap<String, HashSet<String>> bannedWorlds) throws IOException {
        
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
        
        fc.save(new File(plugin.getDataFolder() + "/data/bannedWorlds.yml"));
        
        
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
    
    
    public void saveDaStats(String guid, Boolean optout) throws IOException {
        
        fc = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/dastats.yml"));
        
        fc.set("opt-out", optout);
        fc.set("guid", guid);
        
        
        fc.save(new File(plugin.getDataFolder() + "/data/dastats.yml"));
        
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
