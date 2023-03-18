package me.plugout;

import commands.RegisterCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public final class PlugOut extends JavaPlugin implements Listener {
    public void QuickLog(String message){
        String prefix = "[POU]";
        System.out.println(prefix + " " + message);
    }
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(new RegisterCommand());
    }

    @Override
    public void onDisable() {
        QuickLog("Plugin Killed.");
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent pje){
        Player _player = pje.getPlayer();
        pje.setJoinMessage(ChatColor.GREEN + "++ " + _player.getDisplayName());
    }
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent pqe){
        Player _player = pqe.getPlayer();
        pqe.setQuitMessage(ChatColor.RED + "-- " + _player.getDisplayName());
        QuickLog(_player.getDisplayName() + " ::: " + String.valueOf(_player.getLocation()));
    }


}
