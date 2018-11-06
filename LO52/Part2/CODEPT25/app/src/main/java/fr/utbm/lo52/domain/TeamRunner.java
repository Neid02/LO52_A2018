package fr.utbm.lo52.domain;

public class TeamRunner {

    private long IdRunner;
    private long IdRace;
    private long IdTeam;
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
