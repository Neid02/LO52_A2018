package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Runner;

@Dao
public interface RunnerDao {

    @Query("SELECT * FROM Runner WHERE Id = :Runner")
    LiveData<List<Runner>> getRunner(long Runner);

    @Insert
    long insertRunner(Runner runner);

    @Update
    int updateRunner(Runner runner);

    @Query("DELETE FROM Runner WHERE Id = :IdRunner")
    int deleteRunner(long IdRunner);

}
