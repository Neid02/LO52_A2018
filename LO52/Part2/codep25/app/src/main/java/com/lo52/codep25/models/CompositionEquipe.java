package com.lo52.codep25.models;

import io.realm.RealmObject;

public class CompositionEquipe extends RealmObject {
    private Participant participant;
    private Equipe equipe;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
