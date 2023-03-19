package models;

import java.util.Date;

public class Note {
    public String ID;
    public Date dateCreated;
    public Date getDateCreated() {
        return dateCreated;
    }

    public Note(String ID) {
        this.ID = ID;
        this.dateCreated = new Date();
    }
    public String getID(){
        return this.ID;
    }
}
