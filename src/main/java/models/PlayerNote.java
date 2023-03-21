package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.plugout.PlugOut;
import netscape.javascript.JSObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.events.Event;
import utils.Security;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;

public class PlayerNote extends Note{
    public String playerName;
    public String hashed_passw;
    public double[] playWorldPos;
    public JsonObject[] playWorldInv;

    public PlayerNote(String ID, Player player) {
        super(ID);
        this.playWorldInv = InvToJson(player.getInventory());
        this.playerName = player.getName();
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
        this.playWorldInv = InvToJson(inv);
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

    public static JsonObject[] InvToJson(Inventory inv){
        JsonObject[] obj = new JsonObject[inv.getSize()];
        Gson gson = new Gson();

        for(int i = 0; i < inv.getSize(); i++){
            try{
                obj[i] = gson.fromJson(gson.toJson(inv.getItem(i).serialize()), JsonObject.class);
            }catch (Exception e){
                obj[i] = gson.fromJson(gson.toJson(new ItemStack(Material.AIR, 1).serialize()), JsonObject.class);
            }
        }

        return obj;
    }
    public void SetInventory(Player p){
        JsonObject[] inv = this.playWorldInv;

        Gson gson = new Gson();
        for(int i = 0; i < inv.length; i++){
            ItemStack item = gson.fromJson(gson.toJson(inv[i]), ItemStack.class);
            PlugOut.QuickLog(item.getType().toString());
            if(item.getAmount() == 0) item.setAmount(1);

            p.getInventory().setItem(i, item);
        }
    }
}
