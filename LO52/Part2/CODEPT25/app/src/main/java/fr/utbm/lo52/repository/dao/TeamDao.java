package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Team;


@Dao
public interface TeamDao {

    @Query("SELECT * FROM Team WHERE idTeam = :IdTeam AND idRace = :IdRace")
    LiveData<List<Team>> getTeam(long IdTeam, long IdRace);

    @Insert
    long insertTeam(Team team);

    @Update
    int updateTeam(Team team);

    @Query("DELETE FROM Team WHERE idTeam = :IdTeam AND idRace = :IdRace")
    int deleteTeam(long IdTeam, long IdRace);

}


