package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class TypeCourse implements Serializable {

    public static final String TAG = "TypeCourse";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mTitre;

    public TypeCourse() {
    }

    public TypeCourse(long mId, String mTitre) {
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
        return mTitre;
    }
    public void setmTitre(String mTitre) {
        this.mTitre = mTitre;
    }
}
