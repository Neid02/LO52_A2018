package fr.utbm.experience.tipstop_app.model;

public class Runner {
    private  String r_matricule;
    private String r_name;
    private int r_echelon;
    private int r_team;

    public Runner(String matricule,int team, int r_echelon,String r_name) {
        this.r_team = team;
        this.r_name = r_name;
        this.r_echelon = r_echelon;
        this.r_matricule = matricule;
    }

    public int getTeam() {
        return r_team;
    }

    public void setTeam(int team) {
        this.r_team = team;
    }

    public String getR_matricule() {
        return r_matricule;
    }

    public void setR_matricule(String r_matricule) {
        this.r_matricule = r_matricule;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public int getR_echelon() {
        return r_echelon;
    }

    public void setR_echelon(int r_echelon) {
        this.r_echelon = r_echelon;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return '['+r_matricule+','+ r_team + ',' + r_name + ',' + r_echelon +']' ;
    }
}
