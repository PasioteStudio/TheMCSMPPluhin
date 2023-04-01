package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.plugout.PlugOut;
import models.PlayerNote;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
            return true;
        }

        String input = args[0];
        Security sec = new Security();
        String input_hashed = sec.EncryptSHA256(input);
        String hashed = playerNote.hashed_passw;
        if(!input_hashed.equals(hashed)){
            player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.YELLOW + "Incorrect password. Try again.");
            return true;
        }
        PlugOut p = PlugOut.GetPlugin();
        FileConfiguration conf = p.getConfig();
        World world = p.getServer().getWorld(playerNote.lastPlayWorld);
        double[] pos = playerNote.playWorldPos;
        Location tpTo = new Location(world, pos[0], pos[1], pos[2]);


        playerNote.ActualizePlayWorldInv(player);

        player.teleport(tpTo);
        player.sendMessage(ChatColor.AQUA + "[Login] " + ChatColor.GREEN + "Welcome, " + player.getName() + "!");
        player.setInvulnerable(false);
        return true;
    }
}
