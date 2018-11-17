package com.lo52.codep25.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<Participant> listParticipants;
    RecyclerView.LayoutManager layoutManager;
    ParticipantDao participantDao;
    TextView empty;
    FloatingActionButton floatingActionButton;
    private View view;
    AlertDialog alertDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.participant_fragment, container, false);
        realmc = Realm.getDefaultInstance();
        listParticipants = new ArrayList<>();
        participantDao = new ParticipantDao();
        participantAdapter = new ParticipantAdapter(getContext(), listParticipants);
        recyclerView = view.findViewById(R.id.particioant_list);
        empty = view.findViewById(R.id.empty_list);
        floatingActionButton = view.findViewById(R.id.fab);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(participantAdapter);
        getParticipants();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParticipantDialog();
            }
        });

        return view;


    }

    public void getParticipants() {
        listParticipants.clear();

        List<Participant> participants = participantDao.findAll();
        //realmc.close();
        // Log.d("ListeP", participants.toString());
        if (participants.size() == 0) {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            listParticipants.addAll(participants);
            participantAdapter.notifyDataSetChanged();
        }


    }

    public void addParticipantDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_participant_layout, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.add_participant);
        dialogBuilder.setIcon(R.drawable.ic_participant);
        final EditText nom = dialogView.findViewById(R.id.nom);
        final EditText prenom = dialogView.findViewById(R.id.prenom);
        Button valider =  dialogView.findViewById(R.id.valider);
        Button annuler = dialogView.findViewById(R.id.annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nom.getText().toString().isEmpty() || prenom.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(), R.string.empty_messages, Toast.LENGTH_SHORT).show();

                }else{
                    Participant participant = new Participant();
                    participant.setNom(nom.getText().toString());
                    participant.setPrenom(prenom.getText().toString());
                    participantDao.addParticipant(participant);
                    getParticipants();
                    //realmc.close();
                    alertDialog.dismiss();
                }

            }
        });


       alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}