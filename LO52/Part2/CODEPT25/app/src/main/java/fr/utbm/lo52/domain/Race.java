package fr.utbm.lo52.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Race {

    @PrimaryKey(autoGenerate = true)
    private long Id;

    private String Name;

    private Date Date;


    public Race() {
    }
    public Race(long id, String name, Date date) {
        super();
        this.Id = id;
        this.Name = name;
        this.Date = date;
    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Date getDate() {
        return Date;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setDate(Date date) {
        this.Date = date;
    }
}
