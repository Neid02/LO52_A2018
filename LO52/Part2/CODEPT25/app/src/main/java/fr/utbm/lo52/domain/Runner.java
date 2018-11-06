package fr.utbm.lo52.domain;

public class Runner {

    private long Id;
    private long IdLevel;
    private String FirstName;
    private String LastName;
    private long Echelon;

    public Runner(long id, long idLevel, String firstName, String lastName, long echelon) {
        Id = id;
        IdLevel = idLevel;
        FirstName = firstName;
        LastName = lastName;
        Echelon= echelon;

    }

    public Runner() {

    }

    public long getEchelon() {
        return Echelon;
    }

    public void setEchelon(long echelon) {
        Echelon = echelon;
    }

    public long getId() {
        return Id;

    }

    public void setId(long id) {
        Id = id;
    }

    public long getIdLevel() {
        return IdLevel;
    }

    public void setIdLevel(long idLevel) {
        IdLevel = idLevel;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
