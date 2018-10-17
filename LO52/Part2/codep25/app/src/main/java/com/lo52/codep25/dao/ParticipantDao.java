package com.lo52.codep25.dao;

import android.content.Context;

import com.lo52.codep25.models.Participant;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;

public class ParticipantDao {
    Realm realmc;
    List<Participant> l;
    public ParticipantDao(Context context) {
        realmc = Realm.getDefaultInstance();
    }

    public void addParticipant(Participant participant) {

        if (participant.getId() == null) {
            realmc.beginTransaction();
            Participant p = realmc.createObject(Participant.class, UUID.randomUUID().toString());
            p.setNom(participant.getNom());
            p.setPrenom(participant.getPrenom());
            p.setEchelon(12);
            p.setGroupe(participant.getGroupe());
            p.setEquipe(participant.getEquipe());
            realmc.commitTransaction();
            realmc.close();
        } else {
            realmc.beginTransaction();
            Participant p = realmc.createObject(Participant.class);
            p.setNom(participant.getNom());
            p.setPrenom(participant.getPrenom());
            p.setEchelon(12);
            p.setId(participant.getId());
            p.setGroupe(participant.getGroupe());
            p.setEquipe(participant.getEquipe());
            realmc.commitTransaction();
            realmc.close();
        }
        realmc.close();

    }

    public List<Participant> findAll() {

            RealmQuery query = realmc.where(Participant.class);
           return query.findAll();
    }
}
