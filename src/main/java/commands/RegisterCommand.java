package commands;

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


        return true;
    }
}
