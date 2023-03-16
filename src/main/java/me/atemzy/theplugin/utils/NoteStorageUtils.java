package me.atemzy.theplugin.utils;

import com.google.gson.Gson;
import me.atemzy.theplugin.ThePlugin;
import me.atemzy.theplugin.models.Note;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class NoteStorageUtils {

    private static ArrayList<Note> Thenotes = new ArrayList<>();

    public static Note createNote(Player p, String message){
        try {
            loadNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Note notes = new Note(p.getName(), message, "world", p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), true);
        Thenotes.add(notes);
        try {
            saveNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    /*public static Note findNote(String id){
        try {
            loadNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Note note : Thenotes){
            if (note.getId().equalsIgnoreCase(id)){
                return note;
            }
        }
        return null;
    }*/
    public static List<Note> findAllNotes(){
        try {
            loadNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Thenotes;
    }
    public static void deleteNote(String id){
        try {
            loadNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //linear search
        for (Note note : Thenotes){
            if (note.getId().equalsIgnoreCase(id)){
                Thenotes.remove(note);
                break;
            }
        }
        try {
            saveNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Note updateNote(String id, Note newNote){
        try {
            loadNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Note note : Thenotes){
            if (note.getId().equalsIgnoreCase(id)) {
                note.setPlayerName((newNote.getPlayerName()));
                note.setPsw((newNote.getPsw()));
                note.setLastWorld(newNote.getLastWorld());
                note.setLastX(newNote.getLastX());
                note.setLastY(newNote.getLastY());
                note.setLastZ(newNote.getLastZ());
                note.setIsInWorld(newNote.getIsInWorld());
                System.out.println("W" + note.getIsInWorld());
                try {
                    saveNotes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return note;
            }
        }
        try {
            saveNotes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
    public static void unloadNotes(){
        Thenotes = new ArrayList<>();
    }

    public static void saveNotes() throws IOException {
        Gson gson = new Gson();
        File file = new File(ThePlugin.getPlugin().getDataFolder().getAbsoluteFile() + "/accounts.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file,false);
        gson.toJson(Thenotes, writer);
        writer.flush();
        writer.close();
        System.out.println("Notes Saved!");
        unloadNotes();

    }
    public static void loadNotes()throws IOException{

        Gson gson = new Gson();
        File file = new File(ThePlugin.getPlugin().getDataFolder().getAbsoluteFile() + "/accounts.json");
        if(file.exists()){
            Reader reader = new FileReader(file);
            Note[] n =gson.fromJson(reader, Note[].class);
            Thenotes = new ArrayList<>(Arrays.asList(n));
            System.out.println("Notes loaded");
            reader.close();
        }else {
            Thenotes.add(new Note("start", "start", "world", 0, 0,0,false));
            saveNotes();
        }

    }
}
