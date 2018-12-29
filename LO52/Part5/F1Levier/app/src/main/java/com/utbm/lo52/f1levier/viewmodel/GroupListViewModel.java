package com.utbm.lo52.f1levier.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.widget.ListPopupWindow;

import com.utbm.lo52.f1levier.dao.AppRepository;
import com.utbm.lo52.f1levier.entity.Groupe;
import com.utbm.lo52.f1levier.entity.GroupeParticipant;
import com.utbm.lo52.f1levier.entity.Participant;
import com.utbm.lo52.f1levier.model.GroupeIhm;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class GroupListViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public GroupListViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public List<GroupeIhm> getAllGroupsIhm(){
        List<GroupeIhm> groupesIhm = new ArrayList<>();

        List<Groupe> groupes = getAllGroups();
        if(groupes != null) {
            for(Groupe g : groupes) {
                GroupeIhm groupeIhm = new GroupeIhm();
                groupeIhm.setId(g.getId());
                groupeIhm.setNomGroupe(g.getNomGroupe());
                groupeIhm.setParticipants(getGroupParticipants(g.getId()));
                System.out.println("test " + groupeIhm.getParticipants().size());
                groupesIhm.add(groupeIhm);
            }
        }

        return groupesIhm;
    }

    public List<Groupe> getAllGroups() {
        return appRepository.getGroupeDAO().getAll();
    }

    public List<Participant> getGroupParticipants(int participantId) {
        return appRepository.getGroupeDAO().getParticipants(participantId);
    }

    public List<String> getAllGroupNames() {
        List<String> nomsGroupes = new ArrayList<>();

        for (Groupe g : getAllGroups()) {
            nomsGroupes.add(g.getNomGroupe());
        }

        return nomsGroupes;
    }

    public GroupeIhm getGroupIhmByName(String nom) {
        GroupeIhm groupeIhm = new GroupeIhm();
        Groupe groupe = appRepository.getGroupeDAO().findByName(nom);

        if (groupe != null) {
            groupeIhm.setId(groupe.getId());
            groupeIhm.setNomGroupe(groupe.getNomGroupe());
            groupeIhm.setParticipants(getGroupParticipants(groupe.getId()));
            //System.out.println(groupeIhm.getParticipants().size());
        }

        return groupeIhm;
    }

    public void insert(GroupeIhm groupeIhm) {
        Groupe groupe = new Groupe();
        groupe.setNomGroupe(groupeIhm.getNomGroupe());

        List<GroupeParticipant> groupeParticipants = new ArrayList<>();
        for(Participant p : groupeIhm.getParticipants()) {
            GroupeParticipant groupeParticipant = new GroupeParticipant();
            groupeParticipant.setId_groupe(groupe.getId());
            groupeParticipant.setId_participant(p.getId());
        }

        appRepository.getGroupeDAO().insertAll(groupe);
        appRepository.getGroupeParticipantDAO().insertAll(groupeParticipants);
    }

    public void deleteAll() {
        List<Groupe> groupes = appRepository.getGroupeDAO().getAll();
        appRepository.getGroupeDAO().deleteAll(groupes);

        for(Groupe g : groupes) {
            appRepository.getGroupeParticipantDAO().delete(g.getId());
        }
    }

}
