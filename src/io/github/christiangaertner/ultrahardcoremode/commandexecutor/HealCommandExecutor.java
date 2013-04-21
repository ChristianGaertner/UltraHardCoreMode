/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Christian
 */
public class HealCommandExecutor implements CommandExecutor {
 
    private UltraHardCoreMode plugin;
    private Settings          settings;
 
    public HealCommandExecutor(UltraHardCoreMode plugin, Settings settings) {
            this.plugin = plugin;
            this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        Player player;
        
        if (strings.length == 0) {
            
            if (!(cs instanceof Player)) {
                cs.sendMessage("This command can only be run by a player or add flag to the command.");
                return false;
            } else {
                player = (Player) cs;
                
                //check if player is disabled
                if (settings.isDisabled(player)) {
                    player.sendMessage("You are currently disabled. Please enter UHC in order to be able to issue this command");
                    return true;
                }

            }
            
        } else if (strings.length == 1) {
            
            player = (Bukkit.getServer().getPlayer(strings[0]));
            if (player == null) {
                cs.sendMessage("Please add a player who is online.");
                return false;
            }

        } else {
            cs.sendMessage("Please check your input. Too many arguments.");
            return false;
        }

        boolean heal = false;
        
        PlayerInventory inventory = player.getInventory();

        ItemStack apple = new ItemStack(Material.APPLE, 1);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);

        if (inventory.contains(apple) && inventory.contains(apple)) {
            heal = true;
            inventory.remove(apple);
            inventory.remove(gold);
        }

        if (heal) {
             int currentHealth = player.getHealth();
             player.setHealth((int) (currentHealth + 5));
             player.sendMessage("You regain 2.5 Hearts!");
             return true;
         }
         player.sendMessage("You do not have enough items...!");
         return true;
        

    }

}
