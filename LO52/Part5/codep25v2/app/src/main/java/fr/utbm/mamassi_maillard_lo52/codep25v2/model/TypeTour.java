package fr.utbm.mamassi_maillard_lo52.codep25v2.model;

import java.io.Serializable;

public class TypeTour implements Serializable {

    public static final String TAG = "Employee";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mTitre;
    private Integer mOrdre;
    private TypeCourse mTypeCourse;

    public TypeTour(){

    }

    public TypeTour(long mId, String mTitre, Integer mOrdre, TypeCourse mTypeCourse) {
        this.mId = mId;
        this.mTitre = mTitre;
        this.mOrdre = mOrdre;
        this.mTypeCourse = mTypeCourse;
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

    public Integer getmOrdre() {
        return mOrdre;
    }

    public void setmOrdre(Integer mOrdre) {
        this.mOrdre = mOrdre;
    }

    public TypeCourse getmTypeCourse() {
        return mTypeCourse;
    }

    public void setmTypeCourse(TypeCourse mTypeCourse) {
        this.mTypeCourse = mTypeCourse;
    }
}
