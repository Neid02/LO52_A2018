package adury_csanchez.utbm.f1levier.model;

public class Subscription {

    public static final String TAG = "Subscription";

    private long mId;
    private Team mTeam;
    private Race mRace;

    public Subscription(){}

    public Subscription(Team team, Race race)
    {
        this.mTeam=team;
        this.mRace=race;
    }

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        this.mId=id;
    }

    public Team getTeam()
    {
        return mTeam;
    }

    public void setTeam(Team team)
    {
        this.mTeam=team;
    }

    public Race getRace()
    {
        return mRace;
    }

    public void setRace(Race race)
    {
        this.mRace=race;
    }
}
