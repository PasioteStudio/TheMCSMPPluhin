package me.atemzy.theplugin.menu;

import me.atemzy.theplugin.ThePlugin;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInvMenu extends Menu {

    public PlayerInvMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return PlayerMenuUtility1.getPlayerName();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return false;
    }

    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) throws MenuManagerNotSetupException, MenuManagerException {
        if(inventoryClickEvent.getAction().equals(InventoryAction.PICKUP_ALL)){
            ThePlugin.getPlugin().getServer().getPlayer( PlayerMenuUtility1.getPlayerName()).getInventory().setItem(inventoryClickEvent.getSlot(),new ItemStack(Material.AIR));

        }else{
            ThePlugin.getPlugin().getServer().getPlayer( PlayerMenuUtility1.getPlayerName()).getInventory().setItem(inventoryClickEvent.getSlot(),inventoryClickEvent.getCurrentItem());

        }
    }

    @Override
    public void setMenuItems() {
        Inventory INV = ThePlugin.getPlugin().getServer().getPlayer( PlayerMenuUtility1.getPlayerName()).getInventory();

        for (int i = 0; i < 53; i++){
            if(INV.getItem(i) != null){
                inventory.setItem(i,INV.getItem(i));

            }
        }


    }
}
