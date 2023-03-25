package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import me.plugout.PlugOut;
import netscape.javascript.JSObject;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.yaml.snakeyaml.events.Event;
import utils.Security;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Map;

public class PlayerNote extends Note {
    public String playerName;
    public String hashed_passw;
    public double[] playWorldPos;
    public JsonObject[] playWorldInv;
    public String lastPlayWorld;

    public PlayerNote(String ID, Player player) {
        super(ID);
        SetPlayWorldInv(player);
        this.playerName = player.getName();
    }
    public PlayerNote() {
    }
    public String SetPlayerPassw(String rawpass){
        //encrypt
        Security security = new Security();
        this.hashed_passw = security.EncryptSHA256(rawpass);
        return this.hashed_passw;
    }
    public void SetPlayWorldPos(double[] posArr){
        this.playWorldPos = posArr;
    }
    public void SetPlayWorldInv(Inventory inv){
        Gson gson = new Gson();
        ItemStack[] stacks = inv.getContents();
        this.playWorldInv = new JsonObject[stacks.length];
        for(int i = 0; i < stacks.length; i++){
            try{
                this.playWorldInv[i] = gson.fromJson(gson.toJson(stacks[i].serialize()), JsonObject.class);
            }catch (NullPointerException e){
                this.playWorldInv[i] = gson.fromJson(gson.toJson(new ItemStack(Material.AIR, 1).serialize()), JsonObject.class);
            }

        }
    }
    public void SetPlayWorldInv(Player p){
        SetPlayWorldInv(p.getInventory());
    }
    public void SetPlayWorldInv(ItemStack[] stacks){
        Gson gson = new Gson();
        this.playWorldInv = new JsonObject[stacks.length];
        for(int i = 0; i < stacks.length; i++){
            try{
                this.playWorldInv[i] = gson.fromJson(gson.toJson(stacks[i].serialize()), JsonObject.class);
            }catch (NullPointerException e){
                this.playWorldInv[i] = gson.fromJson(gson.toJson(new ItemStack(Material.AIR, 1).serialize()), JsonObject.class);
            }

        }
    }
    public String getPlayerName(){
        return this.playerName;
    }
    public String getPassw() {
        return this.hashed_passw;
    }
    public double[] getPlayWorldPos() {
        return this.playWorldPos;
    }
    public Object[] getPlayWorldInv() {
        return this.playWorldInv;
    }

    public void ActualizePlayWorldInv(Player player){
        Gson gson = new Gson();
        JsonObject[] jsonObjects = gson.fromJson(gson.toJson(this.playWorldInv), JsonObject[].class);
        ItemStack[] items = new ItemStack[player.getInventory().getSize()];
        for(int i = 0; i < items.length; i++){
            try{
                items[i] = gson.fromJson(jsonObjects[i], ItemStack.class);
            }catch (Exception e){
                items[i] = new ItemStack(Material.AIR, 1);
            }

        }
        player.getInventory().setContents(items);
    }
    public void PreventZeroItems(){
        Gson gson = new Gson();
        JsonObject[] jsonObjects = gson.fromJson(gson.toJson(this.playWorldInv), JsonObject[].class);
        ItemStack[] items = new ItemStack[jsonObjects.length];
        for(int i = 0; i < items.length; i++){
            items[i] = gson.fromJson(jsonObjects[i], ItemStack.class);
            if(items[i].getAmount() != 0) continue;
            items[i].setAmount(1);
        }
        SetPlayWorldInv(items);
    }

}
