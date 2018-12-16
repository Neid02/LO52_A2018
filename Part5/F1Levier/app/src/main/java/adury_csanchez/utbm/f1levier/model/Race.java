package adury_csanchez.utbm.f1levier.model;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import adury_csanchez.utbm.f1levier.DAO.EnrolmentDAO;
import adury_csanchez.utbm.f1levier.DAO.SubscriptionDAO;
import adury_csanchez.utbm.f1levier.DAO.TeamDAO;
import adury_csanchez.utbm.f1levier.utils.RandomNames;

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

    public void createWeightedTeamsForRace(Context c, List<Runner> lr)
    {
        TeamDAO teamDAO = new TeamDAO(c);
        EnrolmentDAO enrolmentDAO = new EnrolmentDAO(c);
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO(c);
        Collections.sort(lr);
        Collections.reverse(lr);
        int nbTeams = (lr.size()+2)/3;
        int nbTeamsOf2 = nbTeams*3-lr.size();
        for(int i = 0;i<nbTeams;i++){
            Team t = teamDAO.createTeam(RandomNames.getRandomCountry());
            subscriptionDAO.createSubscription(t.getId(),this.getId());
        }
        Iterator<Runner> it = lr.iterator();
        List<Team> lt = teamDAO.getTeamsOfRace(this.getId());
        for(int i = 0;i<nbTeams;i++){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
        for(int i = nbTeams-1;i>=0;i--){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
        Collections.sort(lt,new TeamWeightComparator(c));
        for(int i = 0;i<nbTeams-nbTeamsOf2;i++){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
    }

}
