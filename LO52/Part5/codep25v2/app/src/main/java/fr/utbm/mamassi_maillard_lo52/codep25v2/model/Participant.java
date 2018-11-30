package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class Participant implements Serializable {

    public static final String TAG = "Participant";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mNom;
    private String mPrenom;
    private Integer mEchelon;
    private Stage mStage;


    public Participant() {
    }

    public Participant(long mId, String mNom, String mPrenom, Integer mEchelon, Stage mStage) {
        this.mId = mId;
        this.mNom = mNom;
        this.mPrenom = mPrenom;
        this.mEchelon = mEchelon;
        this.mStage = mStage;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmNom() {
        return mNom;
    }

    public void setmNom(String mNom) {
        this.mNom = mNom;
    }

    public Stage getmStage() {
        return mStage;
    }

    public void setmStage(Stage mStage) {
        this.mStage = mStage;
    }

    public Integer getmEchelon() {
        return mEchelon;
    }

    public void setmEchelon(Integer mEchelon) {
        this.mEchelon = mEchelon;
    }

    public String getmPrenom() {
        return mPrenom;
    }

    public void setmPrenom(String mPrenom) {
        this.mPrenom = mPrenom;
    }
}
