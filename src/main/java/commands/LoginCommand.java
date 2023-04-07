package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.plugout.PlugOut;
import models.PlayerNote;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import utils.NoteStorageUtil;
import utils.Security;

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 1) return false;
        Player player = ((Player) sender).getPlayer();
        PlugOut plugin = PlugOut.GetPlugin();
        if(player.getWorld() != plugin.getServer().getWorld(plugin.getConfig().getString("loginSpawnLocation.world"))){ // if already logged in
            player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.YELLOW + "You are already logged in.");
            return true;
        }
        PlayerNote playerNote = NoteStorageUtil.ReadPlayerNote(player);
        if(playerNote == null){
            player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.YELLOW + "Try registering in with " + ChatColor.AQUA + "/register" + ChatColor.YELLOW + ".");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return true;
        }

        String input = args[0];
        Security sec = new Security();
        String input_hashed = sec.EncryptSHA256(input);
        String hashed = playerNote.hashed_passw;
        if(!input_hashed.equals(hashed)){
            player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.YELLOW + "Incorrect password. Try again.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return true;
        }
        PlugOut p = PlugOut.GetPlugin();
        FileConfiguration conf = p.getConfig();

        Location tpTo = playerNote.playWorldLoc;

        if(playerNote.respawnLoc != null) player.setBedSpawnLocation(playerNote.respawnLoc, true);
        PlugOut.QuickLog("x: " + playerNote.respawnLoc.getX());
        PlugOut.QuickLog("y: " + playerNote.respawnLoc.getY());
        PlugOut.QuickLog("z: " + playerNote.respawnLoc.getZ());


        playerNote.ActualizePlayWorldInv(player);

        player.teleport(tpTo);
        player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.GREEN + "Welcome, " + player.getName() + "!");
        player.setInvulnerable(false);
        if(PlugOut.opsAwaitingLogin.contains(player.getName())){
            PlugOut.opsAwaitingLogin.remove(player.getName());
            player.setOp(true);
        }
        return true;
    }
}
