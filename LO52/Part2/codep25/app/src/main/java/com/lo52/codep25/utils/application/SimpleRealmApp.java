package com.lo52.codep25.utils.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SimpleRealmApp extends Application {

    private static SimpleRealmApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
