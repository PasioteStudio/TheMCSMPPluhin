package commands;

import me.plugout.PlugOut;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class SetGlobalSpawn implements CommandExecutor {
    public PlugOut plugin;
    public SetGlobalSpawn(PlugOut p){
        this.plugin = p;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        FileConfiguration conf = plugin.getConfig();

        Location loc = p.getLocation();
        // here is the info we need to save into config.yml under loginSpawnLocation
        String world = loc.getWorld().getName();
        double[] pos = new double[]{loc.getX(), loc.getY(), loc.getZ()};
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        if(args.length == 0) args = new String[]{""};
        // setting it
        if(args[0].equals("toggle")){
            boolean prev = conf.getBoolean("useLoginSpawn"); // a prev a previous. ugye pápa? commentek.
            conf.set("useLoginSpawn", !prev);
            p.sendMessage(!prev ? ChatColor.GREEN + "You have " + ChatColor.UNDERLINE + "enabled" + ChatColor.RESET + ChatColor.GREEN + " global login spawn." : ChatColor.RED + "You have " + ChatColor.UNDERLINE + "disabled" + ChatColor.RESET + ChatColor.RED + " global login spawn.");
            plugin.saveConfig();

            World _world = plugin.getServer().getWorld(conf.getString("loginSpawnLocation.world"));
            List<Player> players = _world.getPlayers();
            for(Player _p : players){
                if(_p.getWorld() != _world) continue;
                _p.setInvulnerable(!prev);
            }
            return true;
        }
        if(args[0].equals("invulnerability")){
            boolean prev = conf.getBoolean("invulnerableInSpawnWorld");
            conf.set("invulnerableInSpawnWorld", !prev);
            p.sendMessage(!prev ? ChatColor.GREEN + "Invulnerability is now " + ChatColor.UNDERLINE + "enabled" + ChatColor.RESET + ChatColor.GREEN + " in spawn world." : ChatColor.RED + "Invulnerability is now " + ChatColor.UNDERLINE + "disabled" + ChatColor.RESET + ChatColor.RED + " in spawn world.");
            plugin.saveConfig();

            World _world = plugin.getServer().getWorld(conf.getString("loginSpawnLocation.world"));
            List<Player> players = _world.getPlayers();
            for(Player _p : players){
                if(_p.getWorld() != _world) continue;
                _p.setInvulnerable(!prev);
            }

            return true;
        }
        conf.set("loginSpawnLocation.x", pos[0]);
        conf.set("loginSpawnLocation.y", pos[1]);
        conf.set("loginSpawnLocation.z", pos[2]);
        conf.set("loginSpawnLocation.world", world);
        conf.set("loginSpawnLocation.yaw", yaw);
        conf.set("loginSpawnLocation.pitch", pitch);
        plugin.saveConfig();

        p.sendMessage( ChatColor.GREEN + "Global spawnpoint set successfully!");
        return true;
    }
}
