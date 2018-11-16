package com.silentpangolin.codep25.Objects;

public class Temps {
    private int id_temps;
    private int duree_temps;
    private int id_crr_temps;
    private int id_typetour_temps;

    public Temps(){}

    public Temps(int id_temps, int duree_temps, int id_crr_temps, int id_typetour_temps) {
        this.id_temps = id_temps;
        this.duree_temps = duree_temps;
        this.id_crr_temps = id_crr_temps;
        this.id_typetour_temps = id_typetour_temps;
    }

    public int getId_temps() { return id_temps; }

    public int getDuree_temps() { return duree_temps; }

    public int getId_crr_temps() { return id_crr_temps; }

    public int getId_typetour_temps() { return id_typetour_temps; }


    public void setId_temps(int id_temps) { this.id_temps = id_temps; }

    public void setDuree_temps(int duree_temps) { this.duree_temps = duree_temps; }

    public void setId_crr_temps(int id_crr_temps) { this.id_crr_temps = id_crr_temps; }

    public void setId_typetour_temps(int id_typetour_temps) { this.id_typetour_temps = id_typetour_temps; }
}
