package me.atemzy.theplugin.commands;

import me.atemzy.theplugin.menu.ListAllPlayer;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class getAllPlayerCommand extends SubCommand {
    @Override
    public String getName() {
        return "getAllPlayer";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "List of all online Player";
    }

    @Override
    public String getSyntax() {
        return "/notes gelAllPlayer";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player)) return;

        if (!commandSender.isOp()){ // gondolom ide jön valami

            return;
        }

        try {
            MenuManager.openMenu(ListAllPlayer.class, (Player) commandSender);
        } catch (MenuManagerException | MenuManagerNotSetupException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] strings) {
        return null;
    }
}
