package com.lo52.codep25.dao;

import android.content.Context;

import com.lo52.codep25.models.Participant;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.Sort;

public class ParticipantDao {
    Realm realmc;
    List<Participant> l;
    public ParticipantDao() {
        //if(realmc.isEmpty())
        realmc = Realm.getDefaultInstance();
    }

    public void addParticipant(Participant participant) {

        if (participant.getId() == null) {
           realmc.beginTransaction();
            Participant p = realmc.createObject(Participant.class, UUID.randomUUID().toString());
            p.setNom(participant.getNom());
            p.setPrenom(participant.getPrenom());
            p.setEchelon(generateEchelon());
            realmc.commitTransaction();
            //realmc.close();
        } else {
           realmc.beginTransaction();
            Participant p = realmc.createObject(Participant.class);
            p.setNom(participant.getNom());
            p.setPrenom(participant.getPrenom());
            p.setEchelon(participant.getEchelon());
            p.setId(participant.getId());
            realmc.commitTransaction();
            //realmc.close();
        }
        realmc.close();

    }

    public List<Participant> findAll() {
//        realmc.beginTransaction();
            RealmQuery query = realmc.where(Participant.class).sort("nom", Sort.ASCENDING) ;
           return query.findAll();
    }

    public int generateEchelon(){
        Random r = new Random();
        int Low = 0;
        int High = 100;
        int Result = r.nextInt(High-Low) + Low;
        return Result;
    }
}
