package com.utbm.lo52.f1levier.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.utbm.lo52.f1levier.entity.GroupeParticipant;

import java.util.List;

@Dao
public interface GroupeParticipantDAO {

    @Insert
    void insertAll(List<GroupeParticipant> groupeParticipants);

    @Query("DELETE FROM GROUPE_PARTICIPANT WHERE ID_GROUPE = :groupeId")
    void delete(int groupeId);

}
