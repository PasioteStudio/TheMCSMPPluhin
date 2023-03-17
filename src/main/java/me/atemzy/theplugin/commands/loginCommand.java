package me.atemzy.theplugin.commands;

import me.atemzy.theplugin.ThePlugin;
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

public class loginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;

        String volt = "0";
        Note voltnote = new Note(p.getName(), "", "", 0, 0, 0, false);
        List<Note> notes = NoteStorageUtils.findAllNotes();
        for (Note note : notes) {
            if (!note.getPlayerName().equalsIgnoreCase(p.getName())) continue;
            volt = "1";
            voltnote = note;
            if (voltnote.getIsInWorld()) {
                volt = "2";
            }

        }
        NoteStorageUtils.unloadNotes();


        if (volt.equals("1")) {
            //tud login-olni, mert már létezik
            if (strings.length > 0) {

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < (strings.length - 1); i++) {
                    stringBuilder.append(strings[i]).append(" ");
                }
                stringBuilder.append(strings[strings.length - 1]);

                if (voltnote.GetHashedPsw().equals(stringBuilder.toString())) {
                    //eltalálta a jelszavát
                    commandSender.sendMessage("You logged in!");
                    PotionEffect[] loginpoes = loginPotionEffects.getPotionEffect();
                    for (PotionEffect loginpoe : loginpoes) {
                        p.removePotionEffect(loginpoe.getType());
                    }
                    Note isInWorldnote = new Note(voltnote.getPlayerName(), voltnote.GetHashedPsw(), voltnote.getLastWorld(), voltnote.getLastX(), voltnote.getLastY(), voltnote.getLastZ(), true);
                    NoteStorageUtils.updateNote(voltnote.getId(), isInWorldnote);
                    Location toLocation = new Location(p.getServer().getWorld(voltnote.getLastWorld()), voltnote.getLastX(), voltnote.getLastY(), voltnote.getLastZ());
                    p.teleport(toLocation);
                    p.setGameMode(GameMode.SURVIVAL);
                    for (int i = 0; i < ThePlugin.Ops.length; i++) {
                        if (p.getName().equals(ThePlugin.Ops[i])) {
                            p.setOp(true);
                        }
                    }


                } else {
                    //rossz jelszó
                    commandSender.sendMessage("Wrong Password!");

                }

            }

        } else if (volt.equals("0")) {
            //nem tud loginolni, mert nincs fiókja
            commandSender.sendMessage("You doesn't have an account, can't login! Try register one!");
        } else if (volt.equals("2")) {
            p.sendMessage("You already in the world, idiot!");

        }


        return true;
    }
}
