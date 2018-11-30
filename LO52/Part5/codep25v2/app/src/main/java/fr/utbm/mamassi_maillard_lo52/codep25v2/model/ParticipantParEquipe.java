package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class ParticipantParEquipe implements Serializable {

    public static final String TAG = "ParticipantParEquipe";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private Integer mOrdre;
    private String mGroupe;
    private Participant mParticipant;
    private Equipe mEquipe;

    public ParticipantParEquipe() {
    }

    public ParticipantParEquipe(long mId, Integer mOrdre, String mGroupe, Participant mParticipant, Equipe mEquipe) {
        this.mId = mId;
        this.mOrdre = mOrdre;
        this.mGroupe = mGroupe;
        this.mParticipant = mParticipant;
        this.mEquipe = mEquipe;
    }

    public Equipe getmEquipe() {
        return mEquipe;
    }

    public void setmEquipe(Equipe mEquipe) {
        this.mEquipe = mEquipe;
    }

    public Participant getmParticipant() {
        return mParticipant;
    }

    public void setmParticipant(Participant mParticipant) {
        this.mParticipant = mParticipant;
    }

    public String getmGroupe() {
        return mGroupe;
    }

    public void setmGroupe(String mGroupe) {
        this.mGroupe = mGroupe;
    }

    public Integer getmOrdre() {
        return mOrdre;
    }

    public void setmOrdre(Integer mOrdre) {
        this.mOrdre = mOrdre;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }
}
