package com.utbm.lo52.f1levier.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.DAO.data.Participant;

import java.util.List;

@Dao
public interface ParticipantDAO {

    @Query("SELECT * FROM Participant")
    List<Participant> getAll();

    @Query("SELECT * FROM Participant WHERE ID_PARTICIPANT IN (:participantIds)")
    List<Participant> loadAllByIds(int[] participantIds);

    @Query("SELECT * FROM Participant WHERE PRENOM LIKE :first AND " +
            "NOM LIKE :last LIMIT 1")
    Participant findByName(String first, String last);

    @Insert
    void insertAll(Participant... participants);

    @Delete
    void delete(Participant participant);
}
