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
    public ItemStack[] playWorldInv;
    public String lastPlayWorld;

    public PlayerNote(String ID, Player player) {
        super(ID);
        this.playWorldInv = new ItemStack[player.getInventory().getSize()];
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
        PlugOut.QuickLog("++++++actualizing...");
        player.getInventory().setContents(this.playWorldInv);
    }

}
