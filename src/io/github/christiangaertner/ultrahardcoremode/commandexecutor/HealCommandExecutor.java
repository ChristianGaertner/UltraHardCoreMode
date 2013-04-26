/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.christiangaertner.ultrahardcoremode.commandexecutor;

import io.github.christiangaertner.ultrahardcoremode.Settings;
import io.github.christiangaertner.ultrahardcoremode.UltraHardCoreMode;
import io.github.christiangaertner.ultrahardcoremode.file.Config;
import org.bukkit.ChatColor;
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
    private Config            config;
 
    public HealCommandExecutor(UltraHardCoreMode plugin, Settings settings, Config config) {
            this.plugin = plugin;
            this.settings = settings;
            this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        if (!settings.globalStatus()) {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.uhcglobalofferror"));
            return true;
        }
        
        
        Player player;
        
        if (strings.length == 0) {
            
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.RED + config.config.getString("alerts.noplayer"));
                return false;
            } else {
                player = (Player) cs;
                
                //check if player is disabled
                if (settings.isDisabled(player)) {
                    player.sendMessage(ChatColor.GRAY + config.config.getString("alerts.disabled"));
                    return true;
                }

            }
            
        } else {
            cs.sendMessage(ChatColor.RED + config.config.getString("alerts.toomanyargs"));
            return false;
        }
        
        
        if (!settings.checkWorld(player.getWorld().getName())) {
            player.sendMessage(ChatColor.RED + config.config.getString("alerts.worlddisabled"));
            return true;
        }
        
        if (player.hasPermission("uhc.denyheal")) {
            player.sendMessage(ChatColor.RED + config.config.getString("alerts.noperms"));
            return true;
        }
        

        
        PlayerInventory inventory = player.getInventory();

        ItemStack apple = new ItemStack(Material.APPLE, 1);
        ItemStack gold = new ItemStack(Material.GOLD_BLOCK, 1);

        if (inventory.containsAtLeast(apple, 1) && inventory.containsAtLeast(gold, 1)) {
           
             int currentHealth = player.getHealth();
             
             if (currentHealth == 20) {
                 player.sendMessage(ChatColor.GREEN + config.config.getString("alerts.heal.already"));
                 return true;
             } else {
                int newHealth = currentHealth + config.config.getInt("settings.regain");
                
                if (newHealth >= 20) {
                    player.setHealth(20);
                    player.sendMessage(ChatColor.GREEN + config.config.getString("alerts.heal.full"));
                } else {
                    player.setHealth(newHealth);
                    float hearts = (float) (((float)config.config.getInt("settings.regain")) / 2.0);
                    player.sendMessage(ChatColor.GREEN + String.format(config.config.getString("alerts.heal.regain"), hearts));
                }
            }
             
            removeInventoryItems(inventory, Material.APPLE, 1);
            removeInventoryItems(inventory, Material.GOLD_BLOCK, 1);
        } else {
            player.sendMessage(ChatColor.RED + config.config.getString("alerts.heal.noitem"));
        }
        
        return true;
        

    }
    
    public static void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
    }

}
