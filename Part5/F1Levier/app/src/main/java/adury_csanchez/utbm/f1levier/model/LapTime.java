package adury_csanchez.utbm.f1levier.model;

public class LapTime {

    public static final String TAG = "LapTime";

    private long mId;
    private Runner mAuthor;
    private Race mRace;
    private int mLapNumber;
    private long mTimeSprint1;
    private long mTimeFractionated1;
    private long mTimePitStop;
    private long mTimeSprint2;
    private long mTimeFractionated2;

    public LapTime() {

    }

    public LapTime(int lapNumber, long timeSprint1, long timeFractionated1, long timePitStop, long timeSprint2, long timeFractionated2)
    {
        this.mLapNumber = lapNumber;
        this.mTimeSprint1 = timeSprint1;
        this.mTimeFractionated1 = timeFractionated1;
        this.mTimePitStop = timePitStop;
        this.mTimeSprint2 = timeSprint2;
        this.mTimeFractionated2 = timeFractionated2;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public Runner getAuthor()
    {
        return mAuthor;
    }

    public void setAuthor(Runner author)
    {
        this.mAuthor = author;
    }


    public Race getRace()
    {
        return mRace;
    }

    public void setRace(Race race)
    {
        this.mRace = race;
    }

    public int getLapNumber()
    {
        return mLapNumber;
    }

    public void setLapNumber(int lapNumber)
    {
        this.mLapNumber=lapNumber;
    }


    public long getTimeSprint1()
    {
        return mTimeSprint1;
    }

    public void setTimeSprint1(long timeSprint1)
    {
        this.mTimeSprint1=timeSprint1;
    }

    public long getTimeFractionated1()
    {
        return mTimeFractionated1;
    }

    public void setTimeFractionated1(long timeFractionated1)
    {
        this.mTimeFractionated1=timeFractionated1;
    }

    public long getTimePitStop()
    {
        return mTimePitStop;
    }

    public void setTimePitStop(long timePitStop)
    {
        this.mTimePitStop=timePitStop;
    }

    public long getTimeSprint2()
    {
        return mTimeSprint2;
    }

    public void setTimeSprint2(long timeSprint2)
    {
        this.mTimeSprint2=timeSprint2;
    }

    public long getTimeFractionated2()
    {
        return mTimeFractionated2;
    }

    public void setTimeFractionated2(long timeFractionated2)
    {
        this.mTimeFractionated2=timeFractionated2;
    }
    public long getGlobalTime(){
        return mTimeFractionated1 + mTimeFractionated2 + mTimePitStop + mTimeSprint1 + mTimeSprint2;
    }
    public int getNbCompletedTurns(){
        int nb = 0;
        if(getTimeSprint1()!=0)nb++;
        if(getTimeFractionated1()!=0)nb++;
        if(getTimePitStop()!=0)nb++;
        if(getTimeSprint2()!=0)nb++;
        if(getTimeFractionated2()!=0)nb++;
        return nb;
    }
    public double getMaxTime(){
        return Math.max(mTimeFractionated1, Math.max(mTimeFractionated2, Math.max(mTimePitStop, Math.max(mTimeSprint1, mTimeSprint2))));
    }
}
