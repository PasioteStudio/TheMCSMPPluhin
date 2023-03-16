package me.atemzy.theplugin.commands;

import me.atemzy.theplugin.ThePlugin;
import me.kodysimpson.simpapi.command.SubCommand;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class teste extends SubCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Just a test";
    }

    @Override
    public String getSyntax() {
        return "/notes teste";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
            if (commandSender instanceof Player) {
                ComponentBuilder message4 = new ComponentBuilder("Kurva anyad David!");
                BaseComponent[] msg = message4.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/say Kurva anyad David!")).create();
                //A szerver írja ki, nem a kliens
                ThePlugin.getPlugin().getServer().spigot().broadcast(msg);
                for (Player p : ThePlugin.getPlugin().getServer().getOnlinePlayers()){
                    ((Player) commandSender).performCommand("msg " + p.getName() + " Szeretem a faszt!");
                }
            }

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] strings) {
        return null;
    }
}
