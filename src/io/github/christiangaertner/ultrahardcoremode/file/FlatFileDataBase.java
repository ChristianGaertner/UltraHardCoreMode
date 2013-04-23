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
import java.util.HashSet;
import java.util.List;
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
            this.initialStart = true;
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
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.yml")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/disabled/players.yml")).createNewFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        //just for saftey
        plugin.reloadConfig();
        
        
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
}
