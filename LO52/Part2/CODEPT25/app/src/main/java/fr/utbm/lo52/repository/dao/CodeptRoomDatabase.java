package fr.utbm.lo52.repository.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import fr.utbm.lo52.domain.Category;
import fr.utbm.lo52.domain.Converters;
import fr.utbm.lo52.domain.Participant;
import fr.utbm.lo52.domain.Race;
import fr.utbm.lo52.domain.Runner;
import fr.utbm.lo52.domain.Team;
import fr.utbm.lo52.domain.TeamRunner;


@Database(entities = {Race.class, Category.class, Runner.class, Team.class, Participant.class, TeamRunner.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CodeptRoomDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CodeptRoomDatabase INSTANCE;

    // --- DAO ---
    public abstract RaceDao raceDao();
    public abstract CategoryDao categoryDaoDao();
    public abstract RunnerDao runnerDao();
    public abstract TeamDao teamDao();
    public abstract ParticipantDao participantDaoDao();
    public abstract TeamRunnerDao teamRunnerDao();

    // --- INSTANCE ---
    public static CodeptRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CodeptRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CodeptRoomDatabase.class, "CodeptDatabase.db")
                           // .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
}
}

