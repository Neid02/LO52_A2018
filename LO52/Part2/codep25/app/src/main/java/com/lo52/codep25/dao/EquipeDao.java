package com.lo52.codep25.dao;

import android.content.Context;

import com.lo52.codep25.models.Equipe;
import com.lo52.codep25.models.Groupe;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;

public class EquipeDao {
    Realm realmc;

    public EquipeDao(Context context) {
        realmc = Realm.getDefaultInstance();

    }


    public void addEquipe(Equipe equipe) {

        if(equipe.getId()==null){
            realmc.beginTransaction();
            Equipe e = realmc.createObject(Equipe.class, UUID.randomUUID().toString());
            e.setName(equipe.getName());
            realmc.commitTransaction();
        }else{
            realmc.beginTransaction();
            Equipe e = realmc.createObject(Equipe.class);
            e.setId(equipe.getId());
            e.setName(equipe.getName());
            realmc.commitTransaction();
        }

    }


    public void deleteEquipe(String id) {
        realmc.beginTransaction();
        Equipe equipe = realmc.where(Equipe.class).equalTo("id",id).findFirst();
        equipe.deleteFromRealm();
        realmc.commitTransaction();
    }


    public void updateEquipe(String id) {
        realmc.beginTransaction();
        Equipe equipe = realmc.where(Equipe.class).equalTo("id",id).findFirst();
        //groupe.u();
        realmc.commitTransaction();
    }


    public List<Equipe> findAll() {
        realmc.beginTransaction();
        RealmQuery query = realmc.where(Equipe.class);
        return query.findAll();

    }
}
