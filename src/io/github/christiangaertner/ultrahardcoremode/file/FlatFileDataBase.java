/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.file;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 * @author Christian
 */
public class FlatFileDataBase {
    
    private UltraHardCoreMode plugin;
    /**
     * Just checks if this is the initial start of the plugin, for checking if we should try to load a database file
     */
    public boolean initialStart = false;
    
    public FlatFileDataBase(UltraHardCoreMode plugin) {
        this.plugin = plugin;
    }
    
    public void initDataBase(){

        
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            this.initialStart = true;
        }
        
        if (!(new File(plugin.getDataFolder(), "config.yml")).exists()) {
            plugin.config.setDefaults();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data")).exists()) {
            (new File(plugin.getDataFolder(), "/data")).mkdir();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled")).exists()) {
            (new File(plugin.getDataFolder(), "/data/disabled")).mkdir();
        }
        
        if (!(new File(plugin.getDataFolder(), "/data/disabled/players.data")).exists()) {
            
            try{
                (new File(plugin.getDataFolder(), "/data/disabled/players.data")).createNewFile();
            } catch(IOException e) {
                plugin.log.log(Level.WARNING, "[UHC] Cannot create databasefile.");
            }
            
        }
        
        //just for saftey
        plugin.reloadConfig();
        
        
    }
    
    @SuppressWarnings("unchecked")
    public Set<String> loadPlayersDisabled(){
        Set<String> players = new HashSet<String>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream((plugin.getDataFolder() + "/data/disabled/players.data")));
            if (ois.readObject() != null) {
                players = (Set<String>) ois.readObject(); //unchecked... but no alternative, no harm in supressing.
            }
            ois.close();
            
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Could not load disabled players from file.");
        } catch (ClassNotFoundException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Could not load disabled players from file correctly.");
        }
        
        return players;
    }
    
    public void savePlayersDisabled(Set<String> players){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream((plugin.getDataFolder() + "/data/disabled/players.data")));
            oos.writeObject(players);
            oos.flush();
            oos.close();
            
        } catch (FileNotFoundException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Could not find databasefile.");
        } catch (IOException ex) {
            plugin.log.log(Level.WARNING, "[UHC] Could not save disabled players to file.");
        }
        
    }
}
