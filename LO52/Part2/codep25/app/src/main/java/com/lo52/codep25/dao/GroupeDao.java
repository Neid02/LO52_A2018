package com.lo52.codep25.dao;

import android.content.Context;

import com.lo52.codep25.models.Groupe;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
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
        }else{
            realmc.beginTransaction();
            Groupe g = realmc.createObject(Groupe.class);
            g.setId(groupe.getId());
            g.setTitle(groupe.getTitle());
            realmc.commitTransaction();
        }

    }


    public void deleteGroupe(Long id) {
        realmc.beginTransaction();
        Groupe groupe = realmc.where(Groupe.class).equalTo("id",id).findFirst();
        groupe.deleteFromRealm();
        realmc.commitTransaction();
    }


    public void updateGroupe(Long id) {
        realmc.beginTransaction();
        Groupe groupe = realmc.where(Groupe.class).equalTo("id",id).findFirst();
        //groupe.u();
        realmc.commitTransaction();
    }


    public List<Groupe> findAll() {
        realmc.beginTransaction();
        RealmQuery query = realmc.where(Groupe.class);
        return query.findAll();

    }
}
