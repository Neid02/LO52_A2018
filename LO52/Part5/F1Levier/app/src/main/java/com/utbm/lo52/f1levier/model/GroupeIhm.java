package com.utbm.lo52.f1levier.model;

import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;


public class GroupeIhm {

    private int id;

    private String nomGroupe;

    private List<Participant> participants;

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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
