package adury_csanchez.utbm.f1levier.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public int getWeight(Context c){
        int w=0;
        for(Enrolment e : new EnrolmentDAO(c).getEnrolmentsOfTeam(getId())){
            w+=e.getRunner().getWeight();
        }
        return w;
    }

    public List<Runner> getRunners(Context c){
        List<Runner> runners = new ArrayList<>();
        for(Enrolment e : new EnrolmentDAO(c).getEnrolmentsOfTeam(getId())){
            runners.add(e.getRunner());
        }
        return runners;
    }

    public List<LapTime> getLapTimes(Context c){
        List<LapTime> lts = new ArrayList<>();
        for(Enrolment e : new EnrolmentDAO(c).getEnrolmentsOfTeam(getId())){
            lts.addAll(e.getRunner().getLapTimes(c));
        }
        return lts;
    }
    public double getGlobalTime(Context c){
        double t=0;
        for(LapTime lt : getLapTimes(c)){
            t=Math.max(t,lt.getMaxTime());
        }
        return t;
    }

}
