package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class Stage implements Serializable {

    public static final String TAG = "Stage";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mTitre;

    public Stage() {
    }

    public Stage(long mId, String mTitre) {
        this.mId = mId;
        this.mTitre = mTitre;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmTitre() {
        return this.mTitre;
    }

    public void setmTitre(String mTitre) {
        this.mTitre = mTitre;
    }
}
