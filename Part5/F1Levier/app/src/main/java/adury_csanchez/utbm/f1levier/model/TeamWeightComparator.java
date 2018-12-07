package adury_csanchez.utbm.f1levier.model;

import android.content.Context;

import java.util.Comparator;

import adury_csanchez.utbm.f1levier.DAO.EnrolmentDAO;

public class TeamWeightComparator implements Comparator<Team>{
    private Context c;
    public TeamWeightComparator(Context c) {
        this.c = c;
    }

    @Override
    public int compare(Team a, Team b){
        return a.getWeight(c)-b.getWeight(c);
    }
}
