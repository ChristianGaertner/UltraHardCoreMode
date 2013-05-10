/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import io.github.christiangaertner.ultrahardcoremode.Stats.DaStats;
import io.github.christiangaertner.ultrahardcoremode.Stats.Metrics;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.HealCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ListCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.PardonCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ResetConfigCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ToogleCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.file.FlatFileDataBase;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerDeathListener;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerJoinListener;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerPortalListener;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerRespawnListener;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerTeleportListener;
import io.github.christiangaertner.ultrahardcoremode.listener.RegainListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Christian
 */
public class UltraHardCoreMode extends JavaPlugin{
    
    
    //bukkit objects
    public static final Logger log = Bukkit.getLogger();
    
    //own objects
    public Settings settings = new Settings();
    public Config config = new Config(this);
    public FlatFileDataBase db = new FlatFileDataBase(this, config);
    public Helper helper = new Helper(this);
    
    
    @Override
    public void onEnable(){
        
        //DATABASE
        db.initDataBase();
        if (!db.initialStart) {
            settings.initHashSetPlayers(db.loadPlayersDisabled());
            settings.initHashSetWorlds(db.loadWorlds());
            settings.initWorldListMode(db.loadWorldMode());
            settings.initBannedWorlds(db.loadBannedWorlds());
            settings.setGlobalStatus(db.loadGlobalStatus());
        }
        
        //STATS STUFF
        try {
            //mcstats.org
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
             log.log(Level.WARNING, "[UHC] Cannot submit to McMetrics.");
        }
        
        
        DaStats stats;
        try {
            //dastats
            stats = new DaStats(this);
            stats.send();
        } catch (Exception ex) {
            log.log(Level.WARNING, "[UHC] Cannot submit to DaStats.");
        }
        
        
        //REGISTER COMMANDS
        getCommand("uhc-toogle")        .setExecutor(new ToogleCommandExecutor          (this, settings, config));
        getCommand("uhc-heal")          .setExecutor(new HealCommandExecutor            (this, settings, config));
        getCommand("uhc-list")          .setExecutor(new ListCommandExecutor            (this, settings, config));
        getCommand("uhc-pardon")        .setExecutor(new PardonCommandExecutor          (this, settings, config));
        getCommand("uhc-resetconfig")   .setExecutor(new ResetConfigCommandExecutor     (this, settings, config));
        
        
        //REGISTER EVENTS
        getServer().getPluginManager().registerEvents(new RegainListener            (this, settings, config, helper), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener       (this, settings, config, helper), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener    (this, settings, config, helper), this);
        getServer().getPluginManager().registerEvents(new PlayerPortalListener      (this, settings, config, helper), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener        (this, settings, config, helper), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener     (this, settings, config, helper), this);
    }
    
    @Override
    public void onDisable(){
        //DATABASE
        db.savePlayersDisabled(settings.getNames());
        db.saveGlobalStatus(settings.globalStatus());
        db.saveBannedWorlds(settings.getBannedWorlds());
        
    }
    
    public boolean checkExec(Player player, World world) {
        if (!settings.globalStatus()) {
            return false;
        }
        
        if (player != null) {
            if (settings.isDisabled(player)) {
                return false;
            }
            
            if (player.hasPermission("uhc.bypass")) {
                return false;
            }
        }
        
        if (world != null) {
            if (!settings.checkWorld(world.getName())) {
                return false;
            }
        }
        
        
        return true;
    }
    

    
    
}
