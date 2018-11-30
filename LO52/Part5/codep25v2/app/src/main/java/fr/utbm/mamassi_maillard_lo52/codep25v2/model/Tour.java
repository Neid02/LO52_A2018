package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class Tour implements Serializable {

    public static final String TAG = "Tour";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mTemps;
    private TypeTour mTypeTour;
    private ParticipantParEquipe mParticipantParEquipe;

    public Tour() {
    }

    public Tour(long mId, String mTemps, TypeTour mTypeTour, ParticipantParEquipe mParticipantParEquipe) {
        this.mId = mId;
        this.mTemps = mTemps;
        this.mTypeTour = mTypeTour;
        this.mParticipantParEquipe = mParticipantParEquipe;
    }

    public ParticipantParEquipe getmParticipantParEquipe() {
        return mParticipantParEquipe;
    }

    public void setmParticipantParEquipe(ParticipantParEquipe mParticipantParEquipe) {
        this.mParticipantParEquipe = mParticipantParEquipe;
    }

    public TypeTour getmTypeTour() {
        return mTypeTour;
    }

    public void setmTypeTour(TypeTour mTypeTour) {
        this.mTypeTour = mTypeTour;
    }

    public String getMtemps() {
        return mTemps;
    }

    public void setMtemps(String mtemps) {
        this.mTemps = mtemps;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }
}
