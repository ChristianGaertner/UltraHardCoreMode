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
    
    private static String urlStats = "http://dagardner-bukkit.appspot.com/stats/uhc";
//    private static String urlStats = "http://localhost:8080/stats/uhc";
    
    private static String urlErr = "http://dagardner-bukkit.appspot.com/error/uhc";
//    private static String urlErr = "http://localhost:8080/error/uhc";
    
    /**
     *
     * @param plugin
     */
    public DaStats(final UltraHardCoreMode plugin) {
        this.plugin = plugin;
        //init poster
        this.poster = new HTTP();
        
    }
    
    public void sendFirst() throws Exception {
        
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
        
        if (!DaStats.sendData) {
            return;
        }
        
        this.poster.setTarget(urlStats);
        this.poster.setParam("guid", DaStats.guid);
        this.poster.setParam("ver", plugin.getDescription().getVersion());
        
        String result = this.poster.get();
        
        this.poster.resetParams();
        
        if (!result.contains("OK")) {
            throw new IOException("RESPONDE CODE NOT 'OK': " + result);
        }
        
    }
    
    public void sendStackTrace(String stackTrace) throws Exception {
        
        if(!DaStats.sendData) {
            return;
        }
        this.poster.setTarget(urlErr);
        this.poster.setParam("guid", DaStats.guid);
        this.poster.setParam("ver", plugin.getDescription().getVersion());
        this.poster.setParam("errreport", stackTrace);
        
        String result = this.poster.get();
        
        this.poster.resetParams();
        
        if (!result.contains("OK")) {
            throw new IOException("RESPONDE CODE NOT 'OK': " + result);
        }
        
        
    }
    
    
}
