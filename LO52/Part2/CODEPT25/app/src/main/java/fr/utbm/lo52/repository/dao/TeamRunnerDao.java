package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Participant;
import fr.utbm.lo52.domain.TeamRunner;

@Dao
public interface TeamRunnerDao {

    @Query("SELECT * FROM TeamRunner WHERE IdRace = :IdRace AND IdTeam = :IdTeam AND IdRunner = :IdRunner")
    LiveData<List<TeamRunner>> getTeamRunner(long IdRunner, long IdRace, long IdTeam);

    @Insert
    long insertTeamRunner(TeamRunner teamRunner);

    @Update
    int updateTeamRunner(TeamRunner teamRunner);

    @Query("DELETE FROM TeamRunner WHERE IdRace = :IdRace AND IdTeam = :IdTeam AND IdRunner = :IdRunner")
    int deleteTeamRunner(long IdRunner, long IdRace, long IdTeam);

}





