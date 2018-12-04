package adury_csanchez.utbm.f1levier.model;

import android.util.Log;

import adury_csanchez.utbm.f1levier.DAO.EnrolmentDAO;

public class Team {

    public static final String TAG = "Team";

    private long mId;
    private String mName;

    public Team(){}

    public Team(String name){
        this.mName=name;
    }

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        this.mId=id;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        this.mName=name;
    }

    public void printLogTeam()
    {
        Log.d(TAG, "id="+mId+", name="+mName);
    }

    public int getWeight(EnrolmentDAO enrolmentDAO){
        int w=0;
        for(Enrolment e : enrolmentDAO.getEnrolmentsOfTeam(getId())){
            w+=e.getRunner().getWeight();
        }
        return w;
    }

}
