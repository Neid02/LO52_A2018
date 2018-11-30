package fr.utbm.lo52.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(primaryKeys = {"IdRace", "IdTeam","IdRunner"},foreignKeys = { @ForeignKey(entity = Team.class,
        parentColumns = {"IdTeam","IdRace"},
        childColumns = {"IdTeam","IdRace"}),@ForeignKey(entity = Runner.class,
        parentColumns = "Id",
        childColumns = "IdRunner")})
public class TeamRunner {


    private long IdRace;
    private long IdTeam;
    private long IdRunner;
    private long Order;

    public TeamRunner(long idRunner, long idRace, long idTeam, long order) {
        IdRunner = idRunner;
        IdRace = idRace;
        IdTeam = idTeam;
        Order = order;
    }

    public TeamRunner() {

    }

    public long getIdRunner() {
        return IdRunner;
    }

    public void setIdRunner(long idRunner) {
        IdRunner = idRunner;
    }

    public long getIdRace() {
        return IdRace;
    }

    public void setIdRace(long idRace) {
        IdRace = idRace;
    }

    public long getIdTeam() {
        return IdTeam;
    }

    public void setIdTeam(long idTeam) {
        IdTeam = idTeam;
    }

    public long getOrder() {
        return Order;
    }

    public void setOrder(long order) {
        Order = order;
    }
}
