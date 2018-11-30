package lo52.utbm.fr.codept25;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.lo52.domain.Participant;
import fr.utbm.lo52.repository.dao.ParticipantDao;
import fr.utbm.lo52.repository.dao.TeamDao;

public class TeamActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TeamDao teamDao;
    ParticipantDao participantDao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity);
        recyclerView= findViewById(R.id.tea;
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

}
