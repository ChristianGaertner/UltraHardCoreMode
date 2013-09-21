package io.github.christiangaertner.ultrahardcoremode;

import io.github.christiangaertner.ultrahardcoremode.Stats.DaStats;
import io.github.christiangaertner.ultrahardcoremode.Stats.ErrorReporter;
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
    public static final Logger LOGGER = Bukkit.getLogger();
    
    //own objects
    public Settings settings = new Settings();
    public Config config = new Config(this);
    public FlatFileDataBase db = new FlatFileDataBase(this, config);
    public Helper helper = new Helper(this);
    public DaStats dastats = new DaStats(this);
    public ErrorReporter errorreporter = new ErrorReporter(this);
    
    
    @Override
    public void onEnable(){
        try {
            //DATABASE
            db.initDataBase();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "[UHC] Couldn' t create database file(s).");
        }
        if (!db.initialStart) {
            settings.initHashSetPlayers(db.loadPlayersDisabled());
            settings.initHashSetWorlds(db.loadWorlds());
            settings.initWorldListMode(db.loadWorldMode());
            settings.initBannedWorlds(db.loadBannedWorlds());
            settings.initPotions(config.config.getBoolean("settings.potion-regain"));
            settings.setGlobalStatus(db.loadGlobalStatus());
        }
        
        
        try {
            //dastats
            dastats.sendFirst();
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "[UHC] Cannot submit to DaStats.");
        }
        
        
        //REGISTER COMMANDS
        getCommand("uhc-toogle")        .setExecutor(new ToogleCommandExecutor          (this, settings, config));
        getCommand("uhc-toggle")        .setExecutor(new ToogleCommandExecutor          (this, settings, config));
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
        try {
            db.savePlayersDisabled(settings.getNames());
            db.saveGlobalStatus(settings.globalStatus());
            db.saveBannedWorlds(settings.getBannedWorlds());
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, "[UHC] Error while saving data.");
        }
        
    }
    
    /**
     * General execution tester
     * @param player
     * @param world
     * @return TRUE if the listener should execute
     */
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
    
    /**
     * The checkExec method for the Regain Listener
     * @param player
     * @param world
     * @param listener
     * @return TRUE if the listener should execute
     */
    public boolean checkExec(Player player, World world, RegainListener listener) {
        if (settings.potions()) {
            return this.checkExec(player, world);
        }
        return false;
        
    }
        

    
    
}
