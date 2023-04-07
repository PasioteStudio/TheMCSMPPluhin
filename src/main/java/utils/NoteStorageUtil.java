package utils;

import me.plugout.PlugOut;
import models.PlayerNote;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
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
        playerConfig.set("Loc", playerNote.playWorldLoc);
        playerConfig.set("RespawnLoc", playerNote.respawnLoc != null ? playerNote.respawnLoc : null);
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
        pNote.playWorldLoc = playerConfig.getLocation("Loc");
        try{
            pNote.respawnLoc = playerConfig.getLocation("RespawnLoc");
        }catch (Exception e){
            PlugOut.QuickLog("readnote - NULL");
            pNote.respawnLoc = null;
        }


        ItemStack[] arr = new ItemStack[player.getInventory().getSize()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = ItemStack.deserialize(playerConfig.getConfigurationSection("Inventory." + i).getValues(false));
            if(arr[i].getAmount() == 0) arr[i].setAmount(1);
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
