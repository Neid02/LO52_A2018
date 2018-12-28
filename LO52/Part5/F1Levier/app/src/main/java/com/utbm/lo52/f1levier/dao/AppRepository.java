package com.utbm.lo52.f1levier.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

public class AppRepository {

    private final AppDatabase db;

    private EtapeDAO etapeDAO;

    private GroupeDAO groupeDAO;

    private ParticipantDAO participantDAO;

    private GroupeParticipantDAO groupeParticipantDAO;

    public AppRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        etapeDAO = db.etapeDAO();
        groupeDAO = db.groupeDAO();
        participantDAO = db.participantDAO();
        groupeParticipantDAO = db.groupeParticipantDAO();
    }

    public EtapeDAO getEtapeDAO() {
        return etapeDAO;
    }

    public GroupeDAO getGroupeDAO() {
        return groupeDAO;
    }

    public ParticipantDAO getParticipantDAO() {
        return participantDAO;
    }

    public GroupeParticipantDAO getGroupeParticipantDAO() { return groupeParticipantDAO; }
}
