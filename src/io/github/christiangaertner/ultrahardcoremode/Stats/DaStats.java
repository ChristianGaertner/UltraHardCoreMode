/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.Stats;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author christian
 */
public class DaStats {
    
    private final UltraHardCoreMode plugin;
    private final HTTP poster;
    private static Boolean sendData;
    private static String guid;
    private static String url = "http://dagardner-bukkit.appspot.com/stats/uhc";
//    private static String url = "http://localhost:8080/stats/uhc";
    
    /**
     *
     * @param plugin
     */
    public DaStats(final UltraHardCoreMode plugin) throws Exception {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        
        this.plugin = plugin;
        
        
        if (this.plugin.db.initialStart) {
            //generate a GUID & save it
            DaStats.guid = UUID.randomUUID().toString();
            plugin.db.saveDaStats(DaStats.guid, false);
            DaStats.sendData = true;
            
        } else {
            //get the GUID
            DaStats.guid = plugin.db.loadDaStats("guid");
            if (plugin.db.loadDaStats("opt-out").equalsIgnoreCase("true")) {
                DaStats.sendData = false;
            } else{
                DaStats.sendData = true;
            }
        }
        
        //init poster
        this.poster = new HTTP();
        
    }
    
    
    public void send() throws Exception {
        
        
        if (!DaStats.sendData) {
            return;
        }
        
        
        String result = this.poster.get(DaStats.url, DaStats.guid, plugin.getDescription().getVersion());
        
        
        if (!result.contains("OK")) {
            throw new IOException("RESPONDE CODE NOT 'OK'");
        }
        
    }
    
    
}
