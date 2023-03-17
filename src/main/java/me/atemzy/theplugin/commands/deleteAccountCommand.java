package me.atemzy.theplugin.commands;

import me.atemzy.theplugin.models.Note;
import me.atemzy.theplugin.utils.NoteStorageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class deleteAccountCommand implements CommandExecutor {
    String id = "";
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        if(!p.isOp()) return true;

        List<Note> notes = NoteStorageUtils.findAllNotes();
        boolean didFindAccount = false;
        for (Note note : notes){
            if(!note.getPlayerName().equals(strings[0])) continue;

            id = note.getId();
            didFindAccount = true;
            p.sendMessage("Account Found And Deleted!");
        }
        if(!didFindAccount) p.sendMessage("Account Not Found!");

        if(id.equals("")){
            p.sendMessage("Please provide an account!");
        }else{
            NoteStorageUtils.deleteNote(id);
        }
        NoteStorageUtils.unloadNotes();
        return true;
    }
}
