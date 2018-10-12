package com.lo52.codep25.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Type extends RealmObject{

    @PrimaryKey
    private long id;
    private int duree;
    private String libelle;
    private Participant participant;
    private Tour tour;
}
