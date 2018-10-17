package com.lo52.codep25.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lo52.codep25.R;
import com.lo52.codep25.adapters.ParticipantAdapter;
import com.lo52.codep25.dao.ParticipantDao;
import com.lo52.codep25.models.Participant;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ParticipantFragment extends Fragment {
    Realm realmc;
    ParticipantAdapter participantAdapter;
    RecyclerView recyclerView;
    ArrayList<Participant>listParticipants;
    RecyclerView.LayoutManager layoutManager;
    ParticipantDao participantDao;
    TextView empty;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.participant_fragment, container, false);
        realmc = Realm.getDefaultInstance();
        listParticipants = new ArrayList<>();
        participantDao = new ParticipantDao(getActivity().getApplicationContext());
        participantAdapter = new ParticipantAdapter(getContext(),listParticipants);
        recyclerView = view.findViewById(R.id.particioant_list);
        empty = view.findViewById(R.id.empty_list);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(participantAdapter);
        getParticipants();


        return view;


    }

    public void getParticipants(){
        listParticipants.clear();

        List<Participant> participants = participantDao.findAll();
        realmc.close();
       // Log.d("ListeP", participants.toString());
        if(participants.size()==0){
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            listParticipants.addAll(participants);
            participantAdapter.notifyDataSetChanged();
        }


    }
}