package com.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.utbm.lo52.f1levier.dao.AppRepository;
import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

public class ParticipantListViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    private LiveData<List<Participant>> allParticipants;

    public ParticipantListViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
        allParticipants = appRepository.getAllParticipants();
    }

    public LiveData<List<Participant>> getAllParticipants() {
        return allParticipants;
    }

}
