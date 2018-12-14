package adury_csanchez.utbm.f1levier.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import adury_csanchez.utbm.f1levier.DAO.RaceDAO;
import adury_csanchez.utbm.f1levier.DAO.TeamDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;

public class TeamResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_result);
        Long raceID = getIntent().getExtras().getLong("RaceID");
        Long teamID = getIntent().getExtras().getLong("TeamID");
        Team team = new TeamDAO(this).getTeamById(teamID);
        Race race = new RaceDAO(this).getRaceById(raceID);
        List<Runner> runners = team.getRunners(this);
        TextView textView = findViewById(R.id.textView);
        textView.setText(" "+team.getName());
        LinearLayout bigContainer = findViewById(R.id.bigContainer);
        for(final Runner runner : runners){
            LinearLayout runnerLayout = new LinearLayout(this);
            runner.getLapTimesForRace(this,race);

        }
    }
}
