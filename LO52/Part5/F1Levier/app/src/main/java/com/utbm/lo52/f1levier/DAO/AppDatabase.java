package com.utbm.lo52.f1levier.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.utbm.lo52.f1levier.DAO.data.Participant;

@Database(entities = {Participant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ParticipantDAO participantDAO();
}
