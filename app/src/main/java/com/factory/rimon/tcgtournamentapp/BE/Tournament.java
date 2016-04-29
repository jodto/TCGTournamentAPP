package com.factory.rimon.tcgtournamentapp.BE;

import java.util.ArrayList;

/**
 * Created by rimon on 4/29/2016.
 */
public class Tournament {

    String name;
    String date;
    String format;
    String edition;
    String rel;
    String location;
    String entryTime;
    String startTime;
    String price;
    String info;
    ArrayList<Player> players;
    Boolean active;

    public Tournament(String name,
            String date,
            String format,
            String edition/*,
            String rel,
            String location,
            String entryTime,
            String startTime,
            String price,
            String info,
            ArrayList<Player> players,
            Boolean active*/)
    {
        this.name = name;
        this.date = date;
        this.format = format;
        this.edition = edition;
        /*this.rel = rel;
        this.location = location;
        this.entryTime = entryTime;
        this.startTime = startTime;
        this.price = price;
        this.info = info;
        this.players = players;
        this.active = active;*/
    }


    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getEdition(){
        return edition;
    }

    public String getFormat(){
        return format;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setEdition(String edition)
    {
        this.edition = edition;
    }

}
