package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetGlobalSpawnCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) return null;
        List<String> options = new ArrayList<>();
        options.add("toggle");
        options.add("invulnerability");
        return options;
    }
}
