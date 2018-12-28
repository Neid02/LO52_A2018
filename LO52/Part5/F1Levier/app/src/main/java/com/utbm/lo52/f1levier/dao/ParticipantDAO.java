package com.utbm.lo52.f1levier.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.entity.Etape;
import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

@Dao
public interface ParticipantDAO {

    @Query("SELECT * FROM PARTICIPANT")
    List<Participant> getAll();

    @Query("SELECT * FROM PARTICIPANT WHERE ID IN (:participantIds)")
    List<Participant> loadAllByIds(int[] participantIds);

    @Query("SELECT * FROM PARTICIPANT WHERE PRENOM LIKE :prenom AND NOM LIKE :nom LIMIT 1")
    Participant findByName(String prenom, String nom);

    @Query("SELECT * FROM PARTICIPANT WHERE POIDS = :poids")
    Participant findByPoids(int poids);

    @Insert
    void insertAll(List<Participant> participants);

    @Insert
    void insert(Participant participant);

    @Delete
    void deleteAll(List<Participant> participants);

    @Delete
    void delete(Participant participant);

}
