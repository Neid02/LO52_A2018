package com.lo52.codep25.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tour extends RealmObject {
    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
