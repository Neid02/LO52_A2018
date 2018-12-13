package com.utbm.lo52.f1levier.DAO.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Participant {

    @PrimaryKey
    @ColumnInfo(name = "ID_PARTICIPANT")
    private int id;

    @ColumnInfo(name = "NOM")
    private String nom;

    @ColumnInfo(name = "PRENOM")
    private String prenom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
