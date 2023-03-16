package me.atemzy.theplugin;

import me.atemzy.theplugin.commands.*;
import me.atemzy.theplugin.models.Note;
import me.atemzy.theplugin.models.loginPotionEffects;
import me.atemzy.theplugin.utils.NoteStorageUtils;
import me.kodysimpson.simpapi.command.CommandList;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.sql.Time;
import java.util.*;

public final class ThePlugin extends JavaPlugin implements Listener{

    private static ThePlugin plugin;
    Timer timer = new Timer();
    public static String[] Ops = {"Atemzy","AttilaHun63"};
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Date asd = new Date();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Hogy a linux vps cmd-je ne telítődjön túl, ez a clear
                System.out.print("\033[H\033[2J"); System.out.flush();
                getServer().spigot().restart();
            }
        }, new Date(
            new Date().getYear(),new Date().getMonth(), new Date().getDate(), 23, 59, 59
        ));
        try {
            CommandManager.createCoreCommand(this, "notes", "Create and list notes.", "/notes", new CommandList() {
                @Override
                public void displayCommandList(CommandSender commandSender, List<SubCommand> list) {
                    commandSender.sendMessage("---------------------------------------");
                    for (SubCommand subCommand:list){
                        commandSender.sendMessage(subCommand.getSyntax()+" - " + subCommand.getDescription());
                    }
                    commandSender.sendMessage("---------------------------------------");

                }
            }, teste.class, getAllPlayerCommand.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        getCommand("register").setExecutor(new registerCommand());
        getCommand("login").setExecutor(new loginCommand());
        getCommand("forgot_password").setExecutor(new forgotloginCommand());
        getCommand("deleteAc").setExecutor(new deleteAccountCommand());
        MenuManager.setup(getServer(), this);
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        List<Player> players = (List<Player>) getServer().getOnlinePlayers();
        List<Note> notese = NoteStorageUtils.findAllNotes();
        for (Player p : players){
            for (Note nota : notese){
                if(p.getName().equals(nota.getPlayerName())){
                    Note newnota;
                    boolean volta = false;
                    for(Player oplayer : getServer().getOnlinePlayers()){
                        if (oplayer == p) {
                            volta = true;
                            break;
                        }
                    }
                    if(volta){
                        newnota = new Note(nota.getPlayerName(), nota.getPsw(), p.getWorld().getName(), p.getLocation().getX(),  p.getLocation().getY(),  p.getLocation().getZ(), false);
                    }else{
                        newnota = new Note(nota.getPlayerName(), nota.getPsw(), nota.getLastWorld(), nota.getLastX(), nota.getLastY(), nota.getLastZ(), false);
                    }



                    NoteStorageUtils.updateNote(nota.getId(), newnota);

                }
            }
            for (String op : Ops) {
                if (p.getName().equals(op)) {
                    p.setOp(false);
                }
            }
        }
        NoteStorageUtils.unloadNotes();
    }

    public static ThePlugin getPlugin() {
        return plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player p = event.getPlayer();
        if(getServer().getWorld("loginHub") != null){
            Location loginHub = new Location(getServer().getWorld("loginHub"),getServer().getWorld("loginHub").getSpawnLocation().getX(), getServer().getWorld("loginHub").getSpawnLocation().getY(), getServer().getWorld("loginHub").getSpawnLocation().getZ() );
            p.teleport(loginHub);

        }else{
            Location loginHub = new Location(getServer().getWorld("world"), getServer().getWorld("world").getSpawnLocation().getX(), getServer().getWorld("world").getSpawnLocation().getY(), getServer().getWorld("world").getSpawnLocation().getZ());
            p.teleport(loginHub);

        }
        p.setGameMode(GameMode.ADVENTURE);
        boolean voltnote =false;

        List<Note> notes = NoteStorageUtils.findAllNotes();
        for (Note note : notes){
            if (note.getPlayerName().equalsIgnoreCase(p.getName())){
                voltnote = true;

            }
        }
        NoteStorageUtils.unloadNotes();
        if (voltnote){
            p.sendMessage(ChatColor.GREEN+ "You have an account!" +ChatColor.RESET +" Login to your account!");
            p.sendMessage("---------------------------------------");
            p.sendMessage("Login with:");
            p.sendMessage(ChatColor.GOLD + "/login <your-password>");
            p.sendMessage("---------------------------------------");
            p.sendMessage("If you do not remember your password than type:");
            p.sendMessage(ChatColor.GOLD +"/forgot_password");
            p.sendMessage("And get help to recover your account!");
            p.sendMessage("---------------------------------------");

        }else{
            p.sendMessage(ChatColor.RED+"You do not have an account!"+  ChatColor.RESET+" Register your account!");
            p.sendMessage("---------------------------------------");
            p.sendMessage("Register with:");
            p.sendMessage(ChatColor.GOLD +"/register <your-password> <confirm-your-password>");
            p.sendMessage("---------------------------------------");

        }

        PotionEffect[] pe =loginPotionEffects.getPotionEffect();
        for (PotionEffect potionEffect : pe) {
            p.addPotionEffect(potionEffect);
        }


    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){

        Player p = event.getPlayer();
        if (!p.isDead()) {
            List<Note> notes = NoteStorageUtils.findAllNotes();
            for (Note note : notes){
                if (note.getPlayerName().equalsIgnoreCase(p.getName())) {
                    if (note.getIsInWorld()) {
                        if (!p.getWorld().getName().equals( "loginHub")){
                            Note newnote = new Note(p.getName(), note.getPsw(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), false);
                            NoteStorageUtils.updateNote(note.getId(), newnote);


                            System.out.println("Quitted succesfuly");
                        }
                    }
                }
            }
            NoteStorageUtils.unloadNotes();
        }
        for (String op : Ops) {
            if (p.getName().equals(op)) {
                p.setOp(false);
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(getServer().getPlayer(event.getEntity().getName()) != null) {
            Player p = getServer().getPlayer(event.getEntity().getName());
            List<Note> notes = NoteStorageUtils.findAllNotes();
            for (Note note : notes) {
                if (note.getPlayerName().equalsIgnoreCase(p.getName())) {
                    if (note.getIsInWorld()) {
                        if (!p.getWorld().getName().equals("loginHub") ){

                            Note newnote;
                            if (p.getBedSpawnLocation() != null) {
                                newnote = new Note(p.getName(), note.getPsw(), p.getWorld().getName(), p.getBedSpawnLocation().getX(), p.getBedSpawnLocation().getY(), p.getBedSpawnLocation().getZ(), false);

                            } else {
                                newnote = new Note(p.getName(), note.getPsw(), p.getWorld().getName(), p.getWorld().getSpawnLocation().getX(), p.getWorld().getSpawnLocation().getY(), p.getWorld().getSpawnLocation().getZ(), false);
                            }
                            NoteStorageUtils.updateNote(note.getId(), newnote);
                            System.out.println("Died succesfuly");
                        }
                    }
                }
            }
            NoteStorageUtils.unloadNotes();
        }
    }


}
