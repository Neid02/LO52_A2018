package adury_csanchez.utbm.f1levier.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import adury_csanchez.utbm.f1levier.DAO.TeamDAO;

public class Race {

    public static final String TAG = "Race";

    private long mId;
    private String mName;

    public Race(){}

    public Race(String name)
    {
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

    public List<Team> getTeams(Context c){
        return new TeamDAO(c).getTeamsOfRace(getId());
    }

    public void printLogRace()
    {
        Log.d(TAG, "id="+mId+", name="+mName);
    }

}
