package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.plugout.PlugOut;
import models.Note;
import models.PlayerNote;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class NoteStorageUtil {
    public static void SavePlayerNote(PlayerNote playerNote) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String p = PlugOut.GetPlugin().getDataFolder() + "\\players\\" + playerNote.ID + ".json";
        File path = new File(p);
        path.getParentFile().mkdir();
        path.createNewFile();
        FileWriter writer = new FileWriter(p);

        String json = gson.toJson(playerNote);
        writer.write(json);
        writer.close();
    }
    public static PlayerNote ReadPlayerNote(Player player){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String p = PlugOut.GetPlugin().getDataFolder() + "\\players\\" + player.getName() + ".json";
        File path = new File(p);
        if(!path.exists()) return null;

        PlayerNote playerNote = null;
        try {
            playerNote = gson.fromJson(new FileReader(p), PlayerNote.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return playerNote;
    }
}
