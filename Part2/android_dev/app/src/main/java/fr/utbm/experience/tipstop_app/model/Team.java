package fr.utbm.experience.tipstop_app.model;

public class Team {
    private int id;
    private int manifestation;
    private int nbreParticipant;
    private int echelon;

    public Team(int manifestation, int nbreParticipant, int echelon) {
        this.manifestation = manifestation;
        this.nbreParticipant = nbreParticipant;
        this.echelon = echelon;
    }
    public Team(int id,int manifestation, int nbreParticipant, int echelon) {
        this.manifestation = manifestation;
        this.nbreParticipant = nbreParticipant;
        this.echelon = echelon;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManifestation() {
        return manifestation;
    }

    public void setManifestation(int manifestation) {
        this.manifestation = manifestation;
    }

    public int getNbreParticipant() {
        return nbreParticipant;
    }

    public void setNbreParticipant(int nbreParticipant) {
        this.nbreParticipant = nbreParticipant;
    }

    public int getEchelon() {
        return echelon;
    }

    public void setEchelon(int echelon) {
        this.echelon = echelon;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", manifestation=" + manifestation +
                ", nbreParticipant=" + nbreParticipant +
                ", echelon=" + echelon +
                '}';
    }
}
