package me.atemzy.theplugin.models;

import java.util.Date;
import java.util.UUID;

public class Note {

    private String id;
    private String playerName;
    private String psw;
    private Date dateCreated;

    private String LastWorld;

    private double LastX;
    private double LastY;
    private double LastZ;
    private boolean IsInWorld;

    public Note(String playerName, String psw, String LastWorld, double LastX,double LastY,double LastZ, boolean IsInWorld) {
        this.playerName = playerName;
        this.psw = psw;
        this.dateCreated = new Date();
        this.id= UUID.randomUUID().toString();
        this.LastWorld = LastWorld;
        this.LastX = LastX;
        this.LastY = LastY;
        this.LastZ = LastZ;
        this.IsInWorld = IsInWorld;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getLastWorld() {
        return LastWorld;
    }

    public void setLastWorld(String lastWorld) {
        LastWorld = lastWorld;
    }
    public boolean getIsInWorld() {
        return IsInWorld;
    }

    public void setIsInWorld(boolean isInWorld) {
        IsInWorld = isInWorld;
    }

    public double getLastX() {
        return LastX;
    }

    public void setLastX(double lastX) {
        LastX = lastX;
    }

    public double getLastY() {
        return LastY;
    }

    public void setLastY(double lastY) {
        LastY = lastY;
    }

    public double getLastZ() {
        return LastZ;
    }

    public void setLastZ(double lastZ) {
        LastZ = lastZ;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
