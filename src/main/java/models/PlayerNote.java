package models;

import me.plugout.PlugOut;
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
    public ItemStack[] playWorldInv;

    public PlayerNote(String ID, Player player) {
        super(ID);
        this.playWorldInv = player.getInventory().getContents();
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
    public void SetPlayWorldInv(ItemStack[] inv){
        this.playWorldInv = inv;
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
    public ItemStack[] getPlayWorldInv() {
        return this.playWorldInv;
    }
}
