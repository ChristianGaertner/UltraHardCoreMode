/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import io.github.christiangaertner.ultrahardcoremode.commandexecutor.HealCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ToogleCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.listener.RegainListener;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Christian
 */
public class UltraHardCoreMode extends JavaPlugin{
    
    public Settings settings = new Settings();
    public Config config = new Config(this);
    
    Logger log = Bukkit.getLogger();
    
    @Override
    public void onEnable(){
        //Config Stuff
        config.setDefaults();
        
        
        //REGISTER COMMANDS
        getCommand("uhc-toogle")    .setExecutor(new ToogleCommandExecutor  (this, settings));
        getCommand("uhc-heal")      .setExecutor(new HealCommandExecutor    (this, settings));
        
        //REGISTER EVENTS
         getServer().getPluginManager().registerEvents(new RegainListener(settings), this);
    }
    
    @Override
    public void onDisable(){       
    }
    

    
    
}
