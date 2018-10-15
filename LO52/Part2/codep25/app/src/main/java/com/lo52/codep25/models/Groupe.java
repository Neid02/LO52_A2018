package com.lo52.codep25.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Groupe extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
//    @LinkingObjects("groupe")
//    private RealmList<Participant> participants = new RealmList<>();
//
//    public RealmList<Participant> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(RealmList<Participant> participants) {
//        this.participants = participants;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
