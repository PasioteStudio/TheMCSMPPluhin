package commands;

import models.PlayerNote;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.NoteStorageUtil;

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 1) return false;
        Player player = ((Player) sender).getPlayer();
        PlayerNote playerNote = NoteStorageUtil.ReadPlayerNote(player);

        String input = args[0];
        String hashed = playerNote.hashed_passw;
        if(input != hashed){

            return true;
        }


        return true;
    }
}
