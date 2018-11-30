package fr.utbm.lo52.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(primaryKeys = {"IdTeam", "IdRace"},
        indices = {@Index(value = {"IdTeam", "IdRace"}, unique = true)},foreignKeys = @ForeignKey(entity = Race.class,
        parentColumns = "Id",
        childColumns = "IdRace"))

public class Team {


    private long IdTeam;
    private long IdRace;
    private String Name;


    public Team(long idTeam, long idRace, String name) {
        IdTeam = idTeam;
        IdRace = idRace;
        Name = name;
    }

    public void setIdTeam(long idTeam) {
        IdTeam = idTeam;
    }

    public long getIdTeam() {
        return IdTeam;
    }

    public Team() {

    }

    public long getIdRace() {
        return IdRace;
    }

    public void setIdRace(long idRace) {
        IdRace = idRace;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
