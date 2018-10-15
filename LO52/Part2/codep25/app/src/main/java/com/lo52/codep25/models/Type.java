package com.lo52.codep25.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Type extends RealmObject{

    @PrimaryKey
    private String id;
    private int duree;
    private String libelle;
    private Participant participant;
    private Tour tour;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
