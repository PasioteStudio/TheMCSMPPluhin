package utils;


import me.plugout.PlugOut;
import models.Note;
import models.PlayerNote;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class NoteStorageUtil {
    public static void SavePlayerNote(PlayerNote playerNote) throws Exception {
        String noteid = playerNote.getPlayerName();
        File file = new File(PlugOut.GetPlugin().getDataFolder().getAbsoluteFile() + "/players/" + noteid + ".json");
        JSONObject obj = new JSONObject();
        obj.put("ID", playerNote.getID());
        obj.put("dateCreated", playerNote.getDateCreated().getTime());
        obj.put("playerName", playerNote.getPlayerName());
        obj.put("hashed_passw", playerNote.getPassw());
        obj.put("playWorldPos", playerNote.getPlayWorldPos());
        obj.put("playWorldInv", Arrays.stream(playerNote.getPlayWorldInv()));

        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        writer.write(obj.toString());

        writer.flush();
        writer.close();
        PlugOut.QuickLog("Written.");
    }
    public static PlayerNote ReadPlayerNote(Player player){
        PlayerNote playerNote = new PlayerNote(player.getName(), player);
        File file = new File(PlugOut.GetPlugin().getDataFolder().getAbsoluteFile() + "/players/" + player.getName() + ".json");
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        String data = reader.next();
        JSONObject obj = new JSONObject(data);

        playerNote.ID = player.getName();
        playerNote.hashed_passw = obj.getString("hashed_passw");
        playerNote.dateCreated = new Date(obj.getLong("dateCreated"));
        playerNote.playerName = player.getName();

        JSONArray arr = obj.getJSONArray("playWorldPos");
        double[] darr = new double[]{};
        for(int i = 0; i < arr.length(); i++){
            darr[i] = arr.getDouble(i);
        }
        playerNote.playWorldPos = darr;

        arr = obj.getJSONArray("playWorldInv");
        Object[] invarr = new ItemStack[]{};
        for(int i = 0; i < arr.length(); i++){
            invarr[i] = arr.getJSONArray(i);
        }
        ItemStack[] itemStack = (ItemStack[]) invarr;
        playerNote.playWorldInv = itemStack;

        reader.close();
        return playerNote;
    }
}
