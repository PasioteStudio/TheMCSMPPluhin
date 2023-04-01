package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.plugout.PlugOut;
import models.Note;
import models.PlayerNote;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.*;

public class NoteStorageUtil {
    public static final String playersDataPath = PlugOut.GetPlugin().getDataFolder() + "/players/";
    private static YamlConfiguration playerConfig = null;
    private static File confFile = null;
    private static boolean _init(String ID){
        String path = playersDataPath + ID + ".yml";
        boolean didCreate = false;
        File file = new File(path);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            didCreate = true;
        }

        YamlConfiguration dataConf = YamlConfiguration.loadConfiguration(file);
        InputStream inStream = PlugOut.GetPlugin().getResource(path);

        if(inStream == null){
            dataConf.setDefaults(new YamlConfiguration());
        }
        confFile = file;
        playerConfig = dataConf;
        return didCreate;
    }
    public static void SavePlayerNote(PlayerNote playerNote) throws Exception {
        _init(playerNote.getPlayerName());
        playerConfig.set("PlayerName", playerNote.playerName);
        playerConfig.set("Password", playerNote.hashed_passw);
        playerConfig.set("Position.x", playerNote.playWorldPos[0]);
        playerConfig.set("Position.y", playerNote.playWorldPos[1]);
        playerConfig.set("Position.z", playerNote.playWorldPos[2]);
        playerConfig.set("Position.world", playerNote.lastPlayWorld);
        for(int i = 0; i < playerNote.playWorldInv.length; i++){
            playerConfig.set("Inventory." + i, playerNote.playWorldInv[i] != null ? playerNote.playWorldInv[i].serialize() : new ItemStack(Material.AIR).serialize());
        }
        SaveConfig();
    }
    public static PlayerNote ReadPlayerNote(Player player){
        boolean didCreate = _init(player.getName());
        File file = new File(playersDataPath + player.getName() + ".yml");
        if(didCreate) return null;
        try {
            Scanner reader = new Scanner(file);
            String str = "";
            while (reader.hasNext()){
                str += reader.next();
            }
            if(str.equals("")) return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PlayerNote pNote = new PlayerNote(player.getName(), player);
        pNote.playerName = playerConfig.getString("PlayerName");
        pNote.hashed_passw = playerConfig.getString("Password");
        pNote.playWorldPos = new double[]{
                playerConfig.getDouble("Position.x"),
                playerConfig.getDouble("Position.y"),
                playerConfig.getDouble("Position.z"),
        };
        pNote.lastPlayWorld = playerConfig.getString("Position.world");
        ItemStack[] arr = new ItemStack[player.getInventory().getSize()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = ItemStack.deserialize(playerConfig.getConfigurationSection("Inventory." + i).getValues(false));
            if(arr[i].getAmount() == 0) arr[i].setAmount(1);
            PlugOut.QuickLog(String.valueOf(arr[i].getAmount()) + " x " + arr[i].getType().toString());
        }
        pNote.playWorldInv = arr;
        return pNote;
    }
    private static void SaveConfig(){
        try {
            playerConfig.save(confFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
