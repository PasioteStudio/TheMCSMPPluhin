package me.atemzy.theplugin.commands;

import com.sun.tools.javac.resources.CompilerProperties;
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
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(p.isOp()){
                List<Note> notes = NoteStorageUtils.findAllNotes();
                boolean volt = false;
                for (Note note : notes){
                    if(note.getPlayerName().equals( strings[0])){
                        id = note.getId();
                        volt = true;
                        p.sendMessage("Account Found And Deleted!");
                    }
                }
                if(!volt){
                    p.sendMessage("Account Not Found!");
                }
                if(id.equals("")){
                    p.sendMessage("Please provide an account!");
                }else{
                    NoteStorageUtils.deleteNote(id);
                }
                NoteStorageUtils.unloadNotes();
            }
        }
        return true;
    }
}
