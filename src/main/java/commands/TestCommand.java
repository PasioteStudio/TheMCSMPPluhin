package commands;

import com.google.gson.Gson;
import me.plugout.PlugOut;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Gson gson = new Gson();
        Player p = (Player) sender;
        p.getInventory().setItem(0, new ItemStack(Material.STICK, 64));
        Inventory inv = p.getInventory();
        PlugOut.QuickLog("test1:");
        try {
            PlugOut.QuickLog(gson.toJson(inv));
        }catch (Exception e){
            PlugOut.QuickLog("test 1 failed.");
        }
        PlugOut.QuickLog("test2:");
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand().getItemMeta()));
        }catch (Exception e){
            PlugOut.QuickLog("test 2 failed.");
        }
        PlugOut.QuickLog("test3:");
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand().getType()));

        }catch (Exception e){
            PlugOut.QuickLog("test 3 failed.");
        }
        PlugOut.QuickLog("test4:");
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand().getData()));

        }catch (Exception e){
            PlugOut.QuickLog("test 4 failed.");
        }
        PlugOut.QuickLog("test5:");
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand()));

        }catch (Exception e){
            PlugOut.QuickLog("test 5 failed.");
        }
        PlugOut.QuickLog("test6:"); //EZ LESZ!!!!!!!!!!!!!!!
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand().serialize()));
        }catch (Exception e){
            PlugOut.QuickLog("test 6 failed.");
        }
        PlugOut.QuickLog("test7:");
        try {
            PlugOut.QuickLog(gson.toJson(p.getInventory().getItemInMainHand().toString()));

        }catch (Exception e){
            PlugOut.QuickLog("test 7 failed.");
        }
        return true;

    }
}
