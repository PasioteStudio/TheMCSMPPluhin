package me.plugout;

import commands.RegisterCommand;
import commands.SetGlobalSpawn;
import commands.SetGlobalSpawnCompleter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.configuration.Configuration;

import java.io.File;


public final class PlugOut extends JavaPlugin implements Listener {
    public void QuickLog(String message){
        String prefix = "[POU]";
        System.out.println(prefix + " " + message);
    }
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("setglobalspawn").setExecutor(new SetGlobalSpawn(this));
        getCommand("setglobalspawn").setTabCompleter(new SetGlobalSpawnCompleter());
    }

    @Override
    public void onDisable() {
        QuickLog("Plugin Killed.");
    }
    @EventHandler
    public void OnHungerChange(FoodLevelChangeEvent e){
        FileConfiguration conf = getConfig();
        boolean isEnabled = conf.getBoolean("useLoginSpawn");
        String w = conf.getString("loginSpawnLocation.world");
        World world = getServer().getWorld(w);
        boolean isPlayer = e.getEntity() instanceof Player;

        if(isEnabled && isPlayer){
            if(e.getEntity().getWorld() != world) return;
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnHealthChange(EntityRegainHealthEvent e){
        FileConfiguration conf = getConfig();
        boolean isEnabled = conf.getBoolean("useLoginSpawn");
        String w = conf.getString("loginSpawnLocation.world");
        World world = getServer().getWorld(w);
        boolean isPlayer = e.getEntity() instanceof Player;

        if(isEnabled && isPlayer){
            if(e.getEntity().getWorld() != world) return;
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e){
        FileConfiguration conf = getConfig();
        boolean isEnabled = conf.getBoolean("useLoginSpawn");
        String w = conf.getString("loginSpawnLocation.world");
        World world = getServer().getWorld(w);
        boolean canOpBypass = conf.getBoolean("canOpBypass");

        if(isEnabled && !canOpBypass){
            if(e.getPlayer().getWorld() != world) return;
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent pje){
        Player _player = pje.getPlayer();
        pje.setJoinMessage(ChatColor.GREEN + "++ " + _player.getDisplayName());
        FileConfiguration config = getConfig();

        boolean useGSpawn = config.getBoolean("useLoginSpawn");
        if(!useGSpawn) return;
        double x = config.getDouble("loginSpawnLocation.x");
        double y = config.getDouble("loginSpawnLocation.y");
        double z = config.getDouble("loginSpawnLocation.z");
        String w = config.getString("loginSpawnLocation.world");
        World world = getServer().getWorld(w);
        _player.setInvulnerable(config.getBoolean("invulnerableInSpawnWorld"));
        Location loc = new Location(world, x, y, z);
        _player.teleport(loc);
    }
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent pqe){
        Player _player = pqe.getPlayer();
        pqe.setQuitMessage(ChatColor.RED + "-- " + _player.getDisplayName());
        QuickLog(_player.getDisplayName() + " ::: " + String.valueOf(_player.getLocation()));
    }


}
