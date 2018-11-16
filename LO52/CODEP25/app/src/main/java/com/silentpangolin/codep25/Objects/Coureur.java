package com.silentpangolin.codep25.Objects;

public class Coureur {
    private int id_crr;
    private String nom_crr;
    private String prenom_crr;
    private int echelon_crr;
    private int ordrepassage_crr;
    private int id_equ_crr;

    public Coureur(){ }

    public Coureur(int id_crr, String nom_crr, String prenom_crr, int echelon_crr, int ordrepassage_crr, int id_equ_crr) {
        this.id_crr = id_crr;
        this.nom_crr = nom_crr;
        this.prenom_crr = prenom_crr;
        this.echelon_crr = echelon_crr;
        this.ordrepassage_crr = ordrepassage_crr;
        this.id_equ_crr = id_equ_crr;
    }

    public void setId_crr(int id_crr) {
        this.id_crr = id_crr;
    }

    public void setNom_crr(String nom_crr) {
        this.nom_crr = nom_crr;
    }

    public void setPrenom_crr(String prenom_crr) {
        this.prenom_crr = prenom_crr;
    }

    public void setEchelon_crr(int echelon_crr) {
        this.echelon_crr = echelon_crr;
    }

    public void setOrdrepassage_crr(int ordrepassage_crr) { this.ordrepassage_crr = ordrepassage_crr; }

    public void setId_equ_crr(int id_equ_crr) {
        this.id_equ_crr = id_equ_crr;
    }


    public int getId_crr() {
        return id_crr;
    }

    public String getNom_crr() {
        return nom_crr;
    }

    public String getPrenom_crr() {
        return prenom_crr;
    }

    public int getEchelon_crr() {
        return echelon_crr;
    }

    public int getOrdrepassage_crr() {
        return ordrepassage_crr;
    }

    public int getId_equ_crr() {
        return id_equ_crr;
    }
}
