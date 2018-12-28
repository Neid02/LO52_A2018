package com.utbm.lo52.f1levier.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.utbm.lo52.f1levier.entity.Etape;
import com.utbm.lo52.f1levier.entity.Groupe;
import com.utbm.lo52.f1levier.entity.GroupeParticipant;
import com.utbm.lo52.f1levier.entity.Participant;

@Database(entities = {Etape.class, Groupe.class, Participant.class, GroupeParticipant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EtapeDAO etapeDAO();

    public abstract GroupeDAO groupeDAO();

    public abstract ParticipantDAO participantDAO();

    public abstract GroupeParticipantDAO groupeParticipantDAO();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "f1_levier").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
