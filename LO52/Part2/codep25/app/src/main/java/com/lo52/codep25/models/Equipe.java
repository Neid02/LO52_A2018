package com.lo52.codep25.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Equipe extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
 /*   @LinkingObjects("equipe")
    private RealmResults<Participant> participants = new RealmList<>();

    public RealmList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(RealmList<Participant> participants) {
        this.participants = participants;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
