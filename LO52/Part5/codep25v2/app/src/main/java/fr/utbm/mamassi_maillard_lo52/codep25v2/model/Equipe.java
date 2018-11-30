package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class Equipe implements Serializable {

    public static final String TAG = "Equipe";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mNom;
    private Course mCourse;

    public Equipe() {
    }

    public Equipe(long mId, String mNom, Course mCourse) {
        this.mId = mId;
        this.mNom = mNom;
        this.mCourse = mCourse;
    }

    public Course getmCourse() {
        return mCourse;
    }

    public void setmCourse(Course mCourse) {
        this.mCourse = mCourse;
    }

    public String getmNom() {
        return mNom;
    }

    public void setmNom(String mNom) {
        this.mNom = mNom;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }
}
