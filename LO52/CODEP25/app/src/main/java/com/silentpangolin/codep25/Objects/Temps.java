package com.silentpangolin.codep25.Objects;

public class Temps {
    private int id_temps;
    private long duree_temps;
    private int id_crr_temps;
    private int id_equ_temps;
    private int id_typetour_temps;
    private long date_temps;

    public Temps(){}

    public Temps(int id_temps, long duree_temps, int id_crr_temps, int id_equ_temps, int id_typetour_temps, long date_temps) {
        this.id_temps = id_temps;
        this.duree_temps = duree_temps;
        this.id_crr_temps = id_crr_temps;
        this.id_equ_temps = id_equ_temps;
        this.id_typetour_temps = id_typetour_temps;
        this.date_temps = date_temps;
    }

    public Temps(long duree_temps, int id_crr_temps, int id_equ_temps, int id_typetour_temps, long date_temps) {
        this.duree_temps = duree_temps;
        this.id_crr_temps = id_crr_temps;
        this.id_equ_temps = id_equ_temps;
        this.id_typetour_temps = id_typetour_temps;
        this.date_temps = date_temps;
    }

    public int getId_temps() { return id_temps; }

    public long getDuree_temps() { return duree_temps; }

    public int getId_crr_temps() { return id_crr_temps; }

    public int getId_typetour_temps() { return id_typetour_temps; }

    public long getDate_temps() { return date_temps; }

    public int getId_equ_temps() { return id_equ_temps; }

    public void setId_temps(int id_temps) { this.id_temps = id_temps; }

    public void setDuree_temps(long duree_temps) { this.duree_temps = duree_temps; }

    public void setId_crr_temps(int id_crr_temps) { this.id_crr_temps = id_crr_temps; }

    public void setId_typetour_temps(int id_typetour_temps) { this.id_typetour_temps = id_typetour_temps; }

    public void setDate_temps(long date_temps) { this.date_temps = date_temps; }

    public void setId_equ_temps(int id_equ_temps) { this.id_equ_temps = id_equ_temps; }

    @Override
    public String toString() {
        return "Temps{" +
                "id_temps=" + id_temps +
                ", duree_temps=" + duree_temps +
                ", id_crr_temps=" + id_crr_temps +
                ", id_equ_temps=" + id_equ_temps +
                ", id_typetour_temps=" + id_typetour_temps +
                ", date_temps=" + date_temps +
                '}';
    }
}
