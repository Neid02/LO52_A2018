package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Participant;
import fr.utbm.lo52.domain.Runner;

@Dao
public interface ParticipantDao {

    @Query("SELECT * FROM Participant WHERE IdRunner = :IdRunner AND IdRace = :IdRace")
    LiveData<List<Participant>> getParticipant(long IdRunner, long IdRace);

    @Insert
    long insertParticipant(Participant participant);

    @Update
    int updateParticipant(Participant participant);

    @Query("DELETE FROM Participant WHERE IdRunner = :IdRunner AND IdRace = :IdRace")
    int deleteParticipant(long IdRunner, long IdRace);

}



