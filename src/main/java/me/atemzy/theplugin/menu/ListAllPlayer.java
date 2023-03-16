package me.atemzy.theplugin.menu;

import me.atemzy.theplugin.ThePlugin;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ListAllPlayer extends Menu {
    public ListAllPlayer(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "List of All Player";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }


    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) throws MenuManagerNotSetupException, MenuManagerException {
        if (inventoryClickEvent.getCurrentItem().getType() == Material.PLAYER_HEAD){
            PlayerMenuUtility1.setPlayerName(inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName());
            MenuManager.openMenu(PlayerInvMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        for(Player p : ThePlugin.getPlugin().getServer().getOnlinePlayers()) {
            ItemStack item = makeItem(Material.PLAYER_HEAD, p.getName(), "Location: "+ p.getLocation(), "Time played: "+p.getPlayerTime(), "Health: "+p.getHealth());
            inventory.addItem(item);
        }


        ItemStack close = makeItem(Material.BARRIER, "Close");
        inventory.setItem(53, close);
    }
}
