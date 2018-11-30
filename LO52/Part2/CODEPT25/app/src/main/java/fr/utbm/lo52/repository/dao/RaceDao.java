package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Race;

@Dao
public interface RaceDao {

    @Query("SELECT * FROM Race WHERE Id = :IdRace")
    LiveData<List<Race>> getRace(long IdRace);


    @Query("SELECT * FROM Race")
     List<Race> getAllRace();

    @Insert
    long insertRace(Race race);

    @Update
    int updateRace(Race race);

    @Query("DELETE FROM Race WHERE Id = :IdRace")
    int deleteRace(long IdRace);

}


