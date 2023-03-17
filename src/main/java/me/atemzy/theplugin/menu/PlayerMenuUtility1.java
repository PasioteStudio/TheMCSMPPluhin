package me.atemzy.theplugin.menu;

import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;

public class PlayerMenuUtility1 extends PlayerMenuUtility {
    private static String noteToDeleete;
    public PlayerMenuUtility1(Player p) {
        super(p);
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName1) {
        playerName = playerName1;
    }

    private static String playerName;

    public static String getNoteToDelete() {
        return noteToDeleete;
    }

    public static void setNoteToDelete(String noteToDeleete1) {noteToDeleete = noteToDeleete1;
    }
}
