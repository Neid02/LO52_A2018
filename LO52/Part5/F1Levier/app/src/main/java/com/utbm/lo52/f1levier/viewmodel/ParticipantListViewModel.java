package com.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.utbm.lo52.f1levier.dao.AppRepository;
import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

public class ParticipantListViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public ParticipantListViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public List<Participant> getAllParticipants() {
        return appRepository.getParticipantDAO().getAll();
    }

    public void insertAll(List<Participant> participants) {
        appRepository.getParticipantDAO().insertAll(participants);
    }

    public void deleteAll() {
        appRepository.getParticipantDAO().deleteAll(getAllParticipants());
    }

    public Participant getParticipantByName(String prenom, String nom) {
        return appRepository.getParticipantDAO().findByName(prenom, nom);
    }
}
