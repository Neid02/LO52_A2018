package fr.utbm.experience.tipstop_app.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;

public class Manifestation {

    private int id;
    private String name;
    private String dateEvent;
    private String place;

    public int getId() {
        return id;
    }

    public Manifestation(int id,String name, String dateEvent, String place) {
        this.name = name;
        this.id = id;
        this.dateEvent = dateEvent;
        this.place = place;
    }

    public Manifestation(String name, String dateEvent, String place) {
        this.name = name;
        this.dateEvent = dateEvent;
        this.place = place;
    }
    public Manifestation() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return '['+ name + ',' + dateEvent + ',' + place +']' ;
    }


}
