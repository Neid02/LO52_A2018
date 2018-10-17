package com.lo52.codep25.dao;

import android.content.Context;

import com.lo52.codep25.models.Groupe;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;

public class GroupeDao {
    Realm realmc;

    public GroupeDao(Context context) {
        realmc = Realm.getDefaultInstance();

    }


    public void addGroupe(Groupe groupe) {

        if(groupe.getId()==null){
            realmc.beginTransaction();
            Groupe g = realmc.createObject(Groupe.class, UUID.randomUUID().toString());
            g.setTitle(groupe.getTitle());
            realmc.commitTransaction();
            realmc.close();
        }else{
            realmc.beginTransaction();
            Groupe g = realmc.createObject(Groupe.class);
            g.setId(groupe.getId());
            g.setTitle(groupe.getTitle());
            realmc.commitTransaction();
            realmc.close();
        }

    }


    public void deleteGroupe(Long id) {
        realmc.beginTransaction();
        Groupe groupe = realmc.where(Groupe.class).equalTo("id",id).findFirst();
        groupe.deleteFromRealm();
        realmc.commitTransaction();
        realmc.close();
    }


    public void updateGroupe(Long id) {
        realmc.beginTransaction();
        Groupe groupe = realmc.where(Groupe.class).equalTo("id",id).findFirst();
        //groupe.u();
        realmc.commitTransaction();
        realmc.close();
    }


    public List<Groupe> findAll() {
        realmc = Realm.getDefaultInstance();
        realmc.beginTransaction();
        RealmQuery query = realmc.where(Groupe.class);
        return query.findAll();


    }
}
