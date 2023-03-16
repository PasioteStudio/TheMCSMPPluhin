package me.atemzy.theplugin.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class forgotloginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        commandSender.sendMessage("Contact me in discord (Atemzy#6921)!");
        ComponentBuilder message = new ComponentBuilder("Or get help in the #help text channel in the Eternal Studios!");
        BaseComponent[] msg = message.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.io/eternalstudios")).create();

        commandSender.spigot().sendMessage(msg);
        return true;
    }
}
