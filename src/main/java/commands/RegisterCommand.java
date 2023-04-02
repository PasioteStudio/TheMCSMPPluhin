package commands;

import me.plugout.PlugOut;
import models.PlayerNote;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import utils.NoteStorageUtil;

import java.io.IOException;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player )) return false; // can only be executed by a player.
        if(args.length != 2 || cmd == null || label == null) return false; // we need DATA, people, we need DATA!
        Player p = (Player) sender;
        PlayerNote search = NoteStorageUtil.ReadPlayerNote(p);
        if(search != null){
            p.sendMessage(
                    ChatColor.AQUA + "[Register] " + ChatColor.YELLOW + "There is an account with this name already. Try logging in with" + ChatColor.AQUA + " /login" + ChatColor.YELLOW + "!"
            );
            return true;
        }

        FileConfiguration conf = PlugOut.GetPlugin().getConfig();
        String passw1 = args[0];
        String passw2 = args[1];

        if(!passw1.equals(passw2)) {//if inputs are NOT identical
            p.sendMessage(ChatColor.AQUA + "[Register] " + ChatColor.YELLOW + "The two passwords provided did NOT match. Try again.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return true;
        }
        PlayerNote playerNote = new PlayerNote(p.getName(), p);
        playerNote.SetPlayerPassw(passw1);

        World toWorld = PlugOut.GetPlugin().getServer().getWorld(conf.getString("defaultPlayWorld"));
        p.teleport(toWorld.getSpawnLocation());
        p.setInvulnerable(false);
        p.setBedSpawnLocation(p.getLocation());

        Location loc = p.getLocation();
        double[] playWorldPos = new double[]{loc.getX(), loc.getY(), loc.getZ()};

        Inventory playWorldInv = p.getInventory();
        playerNote.playWorldInv = playWorldInv.getContents();

        playerNote.SetPlayWorldPos(playWorldPos);
        playerNote.lastPlayWorld = conf.getString("defaultPlayWorld");

        PlugOut.QuickLog("I'm about to save the note.");
        try {
            NoteStorageUtil.SavePlayerNote(playerNote);
        } catch (Exception e) {
            PlugOut.QuickLog(String.valueOf(e));
        }
        if(PlugOut.opsAwaitingLogin.contains(p.getName())){
            PlugOut.opsAwaitingLogin.remove(p.getName());
            p.setOp(true);
        }
        return true;
    }
}
