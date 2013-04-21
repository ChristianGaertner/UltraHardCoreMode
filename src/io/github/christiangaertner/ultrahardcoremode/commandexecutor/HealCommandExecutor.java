/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
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
 
    public HealCommandExecutor(UltraHardCoreMode plugin) {
            this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (!(cs instanceof Player)) {
            cs.sendMessage("This command can only be run by a player or add flag to the command.");
            return false;
        } else {
            boolean heal = false;
            
            Player player = (Player) cs;
            
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
}
