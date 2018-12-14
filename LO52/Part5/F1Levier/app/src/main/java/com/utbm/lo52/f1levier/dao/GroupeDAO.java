package com.utbm.lo52.f1levier.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.entity.Groupe;

import java.util.List;

@Dao
public interface GroupeDAO {

    @Query("SELECT * FROM GROUPE")
    LiveData<List<Groupe>> getAll();

    @Query("SELECT * FROM GROUPE WHERE NOM_GROUPE LIKE :nomGroupe LIMIT 1")
    Groupe findByName(String nomGroupe);

    @Insert
    void insertAll(Groupe... groupes);

    @Delete
    void delete(Groupe groupe);

}
