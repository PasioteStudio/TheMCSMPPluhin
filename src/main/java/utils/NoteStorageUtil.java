package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.plugout.PlugOut;
import models.Note;
import models.PlayerNote;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

public class NoteStorageUtil {
    public static void SavePlayerNote(PlayerNote playerNote) throws Exception {
        File path = new File(PlugOut.GetPlugin().getDataFolder() + "/players/" + playerNote.getPlayerName() + ".json");
        path.getParentFile().mkdirs();
        path.createNewFile();
        FileWriter writer = new FileWriter(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        gson.toJson(playerNote, writer);
        writer.flush();
        writer.close();
    }
    public static PlayerNote ReadPlayerNote(Player player){
        File path = new File(PlugOut.GetPlugin().getDataFolder() + "/players/" + player.getName() + ".json");
        Gson gson = new Gson();
        PlayerNote playerNote = null;
        try{
            Scanner reader = new Scanner(path);
            StringBuilder readOut = new StringBuilder();
            while(reader.hasNext()){
                readOut.append(reader.nextLine());
            }
            playerNote = gson.fromJson(readOut.toString(), PlayerNote.class);
        } catch (Exception e){
            PlugOut.QuickLog(String.valueOf(e));
            return null;
        }
        PlugOut.QuickLog(String.valueOf(playerNote == null));
        return playerNote;
    }
}
