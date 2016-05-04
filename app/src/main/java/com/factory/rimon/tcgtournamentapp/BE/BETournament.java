package com.factory.rimon.tcgtournamentapp.BE;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rimon on 4/29/2016.
 */
public class BETournament implements Serializable{

    String id;
    String title;
    String date;
    String format;
    /*String edition;
    String rel;
    String location;
    String entryTime;
    String startTime;
    String price;
    String info;
    ArrayList<BEPlayer> players;
    Boolean active;*/

    public BETournament(String id, String title,
                        String date,
                        String format
            /*String rel,
            String location,
            String entryTime,
            String startTime,
            String price,
            String info,
            ArrayList<Player> players,
            Boolean active*/)
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.format = format;
        /*this.edition = edition;
        this.rel = rel;
        this.location = location;
        this.entryTime = entryTime;
        this.startTime = startTime;
        this.price = price;
        this.info = info;
        this.players = players;
        this.active = active;*/
    }

    public String getId() {return id;}

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    /*public String getEdition(){
        return edition;
    }*/

    public String getFormat(){
        return format;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

   /* public void setEdition(String edition)
    {
        this.edition = edition;
    }*/

}
