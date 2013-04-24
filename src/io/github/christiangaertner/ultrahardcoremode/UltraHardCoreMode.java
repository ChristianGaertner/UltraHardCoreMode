/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import io.github.christiangaertner.ultrahardcoremode.file.Config;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.HealCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ListCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ResetConfigCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ToogleCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.file.FlatFileDataBase;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerDeathListener;
import io.github.christiangaertner.ultrahardcoremode.listener.PlayerJoinListener;
import io.github.christiangaertner.ultrahardcoremode.listener.RegainListener;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
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
    
    
    @Override
    public void onEnable(){
        
        //DATABASE
        db.initDataBase();
        if (!db.initialStart) {
            settings.initHashSet(db.loadPlayersDisabled());
        }
        
        //REGISTER COMMANDS
        getCommand("uhc-toogle")        .setExecutor(new ToogleCommandExecutor          (this, settings, config));
        getCommand("uhc-heal")          .setExecutor(new HealCommandExecutor            (this, settings, config));
        getCommand("uhc-list")          .setExecutor(new ListCommandExecutor            (this, settings, config));
        getCommand("uhc-resetconfig")   .setExecutor(new ResetConfigCommandExecutor     (this, settings, config));
        
        //REGISTER EVENTS
        getServer().getPluginManager().registerEvents(new RegainListener        (this, settings, config), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener    (this, settings, config), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener   (this, settings, config), this);
    }
    
    @Override
    public void onDisable(){
        //DATABASE
        db.savePlayersDisabled(settings.getNames());
        
    }
    

    
    
}
