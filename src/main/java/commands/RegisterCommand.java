package commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player )) return false; // can only be executed by a player.
        if(args.length != 2 || cmd == null || label == null) return false; // we need DATA, people, we need DATA!
        Player p = (Player) sender;

        String passw1 = args[0];
        String passw2 = args[1];

        if(!passw1.equals(passw2)) {//if inputs are NOT identical
            p.sendMessage(ChatColor.AQUA + "[Register] " + ChatColor.YELLOW + "The two passwords provided did NOT match. Try again.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return true;
        }


        return true;
    }
}
