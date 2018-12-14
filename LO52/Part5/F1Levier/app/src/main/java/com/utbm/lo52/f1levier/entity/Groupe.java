package com.utbm.lo52.f1levier.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "GROUPE")
public class Groupe {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_GROUPE")
    private int id;

    @NonNull
    @ColumnInfo(name = "NOM_GROUPE")
    private String nomGroupe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }
}
