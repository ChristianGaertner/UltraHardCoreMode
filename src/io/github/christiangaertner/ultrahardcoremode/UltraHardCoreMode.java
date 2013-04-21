/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode;

import io.github.christiangaertner.ultrahardcoremode.commandexecutor.CheckCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.HealCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.commandexecutor.ToogleCommandExecutor;
import io.github.christiangaertner.ultrahardcoremode.listener.RegenListener;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Christian
 */
public class UltraHardCoreMode extends JavaPlugin{
       
    Logger log = Bukkit.getLogger();
    
    @Override
    public void onEnable(){
        //REGISTER COMMANDS
        getCommand("uhc-toogle")    .setExecutor(new ToogleCommandExecutor(this));
        getCommand("uhc-check")     .setExecutor(new CheckCommandExecutor(this));
        getCommand("uhc-heal")      .setExecutor(new HealCommandExecutor(this));
        
        //REGISTER EVENTS
         getServer().getPluginManager().registerEvents(new RegenListener(), this);
    }
    
    @Override
    public void onDisable(){
    }
    

    
    
}
