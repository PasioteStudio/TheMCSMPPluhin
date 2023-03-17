package me.atemzy.theplugin.commands;

import me.atemzy.theplugin.models.Note;
import me.atemzy.theplugin.models.loginPotionEffects;
import me.atemzy.theplugin.utils.NoteStorageUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class registerCommand  implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player){



            Player p = (Player) commandSender;

            String volt = "0";
            List<Note> notes = NoteStorageUtils.findAllNotes();
            for (Note note : notes) {
                if (note.getPlayerName().equalsIgnoreCase(p.getName())) {
                    volt = "1";
                    if(note.getIsInWorld()){
                        volt = "2";

                    }
                }
            }
            NoteStorageUtils.unloadNotes();

            if(volt.equals("1")){
                //nem tud regisztrálni, mert már létezik
                commandSender.sendMessage("You already have an account, can't register! Try login to that one!");
            }else if(volt.equals("0")){
                //tud regisztrálni, létrehoz egy fiókot
                if(strings.length >0){
                    if (strings[0].equals(strings[1])){
                        //confirm megegyezik

                        NoteStorageUtils.createNote(p, strings[0]);
                        Location Spawn = new Location(p.getServer().getWorld("world"), p.getServer().getWorld("world").getSpawnLocation().getX(), p.getServer().getWorld("world").getSpawnLocation().getY(), p.getServer().getWorld("world").getSpawnLocation().getZ());
                        PotionEffect[] loginpoes = loginPotionEffects.getPotionEffect();
                        for (PotionEffect loginpoe : loginpoes) {
                            p.removePotionEffect(loginpoe.getType());
                        }

                        p.teleport(Spawn);
                        p.setGameMode(GameMode.SURVIVAL);
                    }else {
                        //nem egyezik meg a confirm
                        p.sendMessage("The confirm and the password does not match");
                    }


                }
            }else if(volt.equals("2")){
                p.sendMessage("You already in the world, idiot!");
            }
        }
        return true;
    }
}
