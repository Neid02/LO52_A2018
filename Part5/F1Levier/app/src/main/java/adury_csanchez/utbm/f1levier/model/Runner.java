package adury_csanchez.utbm.f1levier.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import adury_csanchez.utbm.f1levier.DAO.LapTimeDAO;
import adury_csanchez.utbm.f1levier.DAO.RunnerDAO;

public class Runner implements Comparable<Runner>{

    public static final String TAG = "Runner";

    private long mId;
    private String mFirstName;
    private String mLastName;
    private int mWeight;
    //private Team mTeam;

    public Runner(){}

    public Runner(String firstName, String lastName, int weight){
        this.mFirstName=firstName;
        this.mLastName=lastName;
        this.mWeight=weight;
    }

    public long getId(){
        return mId;
    }

    public void setId(long id){
        this.mId=id;
    }

    public String getFistName()
    {
        return mFirstName;
    }

    public void setFirstName(String firstName)
    {
        this.mFirstName=firstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName(String lastName)
    {
        this.mLastName=lastName;
    }

    public int getWeight()
    {
        return mWeight;
    }

    public void setWeight(int weight)
    {
        this.mWeight = weight;
    }

    public List<LapTime> getLapTimes(Context c){
        return new LapTimeDAO(c).getLapTimesOfRunner(getId());
    }

    public void printLogRunner()
    {
        Log.d(TAG, "id="+mId+", firstName="+mFirstName+", lastName="+mLastName+", weight="+mWeight);
    }

    @Override
    public int compareTo(@NonNull Runner runner) {
        return getWeight()-runner.getWeight();
    }
}
