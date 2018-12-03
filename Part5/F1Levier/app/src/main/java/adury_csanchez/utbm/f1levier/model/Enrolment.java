package adury_csanchez.utbm.f1levier.model;

public class Enrolment {

    public static final String TAG = "Enrolment";

    private long mId;
    private Runner mRunner;
    private Team mTeam;

    public Enrolment(){}

    public Enrolment(Runner runner, Team team)
    {
        this.mRunner=runner;
        this.mTeam=team;
    }

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        this.mId=id;
    }

    public Runner getRunner()
    {
        return mRunner;
    }

    public void setRunner(Runner runner)
    {
        this.mRunner=runner;
    }

    public Team getTeam()
    {
        return mTeam;
    }

    public void setTeam(Team team)
    {
        this.mTeam=team;
    }
}
