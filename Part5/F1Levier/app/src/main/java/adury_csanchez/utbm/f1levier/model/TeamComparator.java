package adury_csanchez.utbm.f1levier.model;

import java.util.Comparator;

import adury_csanchez.utbm.f1levier.DAO.EnrolmentDAO;

public class TeamComparator implements Comparator<Team>{
    EnrolmentDAO enrolmentDAO;
    public TeamComparator(EnrolmentDAO enrolmentDAO) {
        this.enrolmentDAO = enrolmentDAO;
    }

    @Override
    public int compare(Team a, Team b){
        return a.getWeight(enrolmentDAO)-b.getWeight(enrolmentDAO);
    }
}
