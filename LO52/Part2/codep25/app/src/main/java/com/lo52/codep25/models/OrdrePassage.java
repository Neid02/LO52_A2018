package com.lo52.codep25.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OrdrePassage extends RealmObject {
    @PrimaryKey
    private String id;
    private int ordre;
    private Participant participant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}