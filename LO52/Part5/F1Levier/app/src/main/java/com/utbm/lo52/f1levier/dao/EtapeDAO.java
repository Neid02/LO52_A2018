package com.utbm.lo52.f1levier.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.entity.Etape;

import java.util.List;

@Dao
public interface EtapeDAO {

    @Query("SELECT * FROM ETAPE")
    LiveData<List<Etape>> getAll();

    @Insert
    void insertAll(Etape... etapes);

    @Delete
    void delete(Etape etape);
}
