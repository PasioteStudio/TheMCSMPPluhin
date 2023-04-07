package models;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import utils.Security;

public class PlayerNote extends Note {
    public String playerName;
    public String hashed_passw;
    public Location playWorldLoc;
    public ItemStack[] playWorldInv;
    public Location respawnLoc;

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
    public String getPlayerName(){
        return this.playerName;
    }
    public String getPassw() {
        return this.hashed_passw;
    }
    public Object[] getPlayWorldInv() {
        return this.playWorldInv;
    }

    public void ActualizePlayWorldInv(Player player){
        player.getInventory().setContents(this.playWorldInv);
    }

}
