package adury_csanchez.utbm.f1levier.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adury_csanchez.utbm.f1levier.DAO.LapTimeDAO;
import adury_csanchez.utbm.f1levier.DAO.RunnerDAO;
import adury_csanchez.utbm.f1levier.utils.RandomNames;

public class Runner implements Comparable<Runner>, Serializable {

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

    public List<LapTime> getLapTimesForRace(Context c,Race race){
        return new LapTimeDAO(c).getLapTimesOfRunnerForRace(getId(),race.getId());
    }

    public void printLogRunner()
    {
        Log.d(TAG, "id="+mId+", firstName="+mFirstName+", lastName="+mLastName+", weight="+mWeight);
    }

    public List<Runner> createRandomRunners(Context context, int numberToGenerate){
        RunnerDAO runnerDAO = new RunnerDAO(context);
        Random rd = new Random();
        List<Runner> lr = new ArrayList<>();
        for(int i = 0;i<numberToGenerate;i++) {
            Runner runner = runnerDAO.createRunner(RandomNames.getRandomFirstName(),RandomNames.getRandomLastName(),rd.nextInt(100));
            lr.add(runner);
        }
        return lr;
    }

    @Override
    public int compareTo(@NonNull Runner runner) {
        return getWeight()-runner.getWeight();
    }
}
