package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class Course implements Serializable {

    public static final String TAG = "Course";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mTitre;
    private String mDate;
    private Stage mStage;
    private TypeCourse mTypeCourse;

    public Course() {
    }

    public Course(long mId, String mTitre, String mDate, Stage mStage, TypeCourse mTypeCourse) {
        this.mId = mId;
        this.mTitre = mTitre;
        this.mDate = mDate;
        this.mStage = mStage;
        this.mTypeCourse = mTypeCourse;
    }

    public TypeCourse getmTypeCourse() {
        return mTypeCourse;
    }

    public void setmTypeCourse(TypeCourse mTypeCourse) {
        this.mTypeCourse = mTypeCourse;
    }

    public Stage getmStage() {
        return mStage;
    }

    public void setmStage(Stage mStage) {
        this.mStage = mStage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTitre() {
        return mTitre;
    }

    public void setmTitre(String mTitre) {
        this.mTitre = mTitre;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }
}
