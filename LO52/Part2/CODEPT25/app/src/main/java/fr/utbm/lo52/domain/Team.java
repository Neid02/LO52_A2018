package fr.utbm.lo52.domain;

public class Team {


    private long IdTeam;
    private long IdRace;
    private String Name;


    public Team(long id, long idRace, String name) {
        IdTeam = id;
        IdRace = idRace;
        Name = name;
    }

    public Team() {
    }

    public long getId() {
        return IdTeam;
    }

    public void setId(long id) {
        IdTeam = id;
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
