package com.utbm.lo52.f1levier.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

@Dao
public interface ParticipantDAO {

    @Query("SELECT * FROM PARTICIPANT")
    LiveData<List<Participant>> getAll();

    @Query("SELECT * FROM PARTICIPANT WHERE ID_PARTICIPANT IN (:participantIds)")
    List<Participant> loadAllByIds(int[] participantIds);

    @Query("SELECT * FROM PARTICIPANT WHERE PRENOM LIKE :prenom AND NOM LIKE :nom LIMIT 1")
    Participant findByName(String prenom, String nom);

    @Insert
    void insertAll(Participant... participants);

    @Delete
    void delete(Participant participant);
}
