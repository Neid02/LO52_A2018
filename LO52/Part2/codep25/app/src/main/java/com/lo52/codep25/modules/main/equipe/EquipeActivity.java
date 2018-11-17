package com.lo52.codep25.modules.main.equipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lo52.codep25.R;
import com.lo52.codep25.adapters.EquipeAdapter;
import com.lo52.codep25.dao.EquipeDao;
import com.lo52.codep25.dao.ParticipantDao;
import com.lo52.codep25.models.Equipe;
import com.lo52.codep25.models.Participant;

import java.util.ArrayList;
import java.util.List;

public class EquipeActivity extends AppCompatActivity {
    EquipeDao equipeDao;
    ParticipantDao participantDao;
    EquipeAdapter equipeAdapter;
    ArrayList<Equipe> listEquipe;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipe_activity);
        recyclerView= findViewById(R.id.equipe_list);
        listEquipe = new ArrayList<>();

        equipeAdapter = new EquipeAdapter(getApplicationContext(),listEquipe);
        recyclerView.setLayoutManager( new GridLayoutManager(this,2));
        recyclerView.setAdapter(equipeAdapter);
        equipeDao = new EquipeDao(getApplicationContext());
        participantDao = new ParticipantDao();
        equipeDao.deleteAllEquipe();
        List<Participant> participantList = participantDao.findAll();
        int nbre_equipe = (participantList.size() / 3);
        for(int i=0; i<nbre_equipe; i++){

            Equipe equipe = new Equipe();
            equipe.setName("Equipe"+Integer.valueOf(i+1));
            equipeDao.addEquipe(equipe);
        }
        getEquipe();
    }

    public void getEquipe(){

        List<Equipe> myListe = equipeDao.findAll();
        listEquipe.addAll(myListe);
        equipeAdapter.notifyDataSetChanged();
     }
}
