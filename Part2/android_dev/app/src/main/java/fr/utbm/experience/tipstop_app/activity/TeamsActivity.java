package fr.utbm.experience.tipstop_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.RunnerDao;
import fr.utbm.experience.tipstop_app.dao.TeamDao;
import fr.utbm.experience.tipstop_app.model.Team;

public class TeamsActivity extends AppCompatActivity {

    private   RunnerDao runnerDao;
    private  TeamDao teamDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        runnerDao = new RunnerDao(this);
        runnerDao.open();
        teamDao = new TeamDao(this);
        teamDao.open();


        List<Integer> teamsIdAvailable = runnerDao.getAllTeamAvailable();
        // get and set the team containing runner
        List<Team> teamAvailable = teamDao.getTeamAvailable(teamsIdAvailable);
        final ArrayAdapter<Team> adapterListTeam = new ArrayAdapter<Team>(this,
                android.R.layout.simple_list_item_1, teamAvailable);

        final ListView listTeam = this.findViewById(R.id.list_time);
        listTeam.setAdapter(adapterListTeam);
        listTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intentTeam = new Intent(TeamsActivity.this, ListRunnerActivity.class);

               // Log.d("Team-id",Integer.toString(adapterListTeam.getItem(position).getId()));
                intentTeam.putExtra("id", Integer.toString(adapterListTeam.getItem(position).getId()));

                startActivity(intentTeam);
            }
        });



    }

}
