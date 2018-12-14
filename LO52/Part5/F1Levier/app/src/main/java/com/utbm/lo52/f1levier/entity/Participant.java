package com.utbm.lo52.f1levier.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "PARTICIPANT")
public class Participant {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PARTICIPANT")
    private int id;

    @NonNull
    @ColumnInfo(name = "NOM")
    private String nom;

    @NonNull
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
