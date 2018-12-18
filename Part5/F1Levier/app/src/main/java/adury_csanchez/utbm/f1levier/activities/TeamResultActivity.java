package adury_csanchez.utbm.f1levier.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import adury_csanchez.utbm.f1levier.DAO.RaceDAO;
import adury_csanchez.utbm.f1levier.DAO.TeamDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.LapTime;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;
import adury_csanchez.utbm.f1levier.utils.Utils;

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
        //TextView textViewTeamName = findViewById(R.id.txt_team_name);
        //textViewTeamName.setText(" "+team.getName());

        // Set action bar title
        getSupportActionBar().setTitle("Resultat de l'équipe : "+race.getName());

        LinearLayout bigContainer = findViewById(R.id.bigContainer);
        bigContainer.setOrientation(LinearLayout.VERTICAL);

        for(final Runner runner : runners) {

            // Runner Name
            TextView textViewRunnerName = new TextView(this);
            textViewRunnerName.setText(runner.getFistName() + " " + runner.getLastName() + " (" + runner.getWeight() + ")");
            bigContainer.addView(textViewRunnerName);
            textViewRunnerName.setTextSize(Utils.mapPXtoDP(this, 6));
            textViewRunnerName.setGravity(Gravity.CENTER);
            textViewRunnerName.setPadding(Utils.mapPXtoDP(this, 2), Utils.mapPXtoDP(this, 5), Utils.mapPXtoDP(this, 2), Utils.mapPXtoDP(this, 5));


            // Grid for results
            GridLayout grid = new GridLayout(this);
            grid.setColumnCount(6);

            TextView tv;

            tv = new TextView(this);
            tv.setText("Tour");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);

            tv = new TextView(this);
            tv.setText("Sprint 1");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);

            tv = new TextView(this);
            tv.setText("Fractionné 1");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);

            tv = new TextView(this);
            tv.setText("Pit Stop");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);

            tv = new TextView(this);
            tv.setText("Sprint 2");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);

            tv = new TextView(this);
            tv.setText("Fractionné 2");
            tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
            grid.addView(tv);


            for (final LapTime lapTime : runner.getLapTimesForRace(this, race)) {

                tv = new TextView(this);
                tv.setText(Integer.toString(lapTime.getLapNumber()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);

                tv = new TextView(this);
                tv.setText(Utils.formatTime(lapTime.getTimeSprint1()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);

                tv = new TextView(this);
                tv.setText(Utils.formatTime(lapTime.getTimeFractionated1()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);

                tv = new TextView(this);
                tv.setText(Utils.formatTime(lapTime.getTimePitStop()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);

                tv = new TextView(this);
                tv.setText(Utils.formatTime(lapTime.getTimeSprint2()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);

                tv = new TextView(this);
                tv.setText(Utils.formatTime(lapTime.getTimeFractionated2()));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.layout.grid_items_border);
                tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                grid.addView(tv);
            }

            bigContainer.addView(grid);
        }
/*
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
        bigContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout legende = new LinearLayout(this);
        legende.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setText("Runner : ");
        legende.addView(tv);
        tv = new TextView(this);
        tv.setText("Sprint1 : ");
        legende.addView(tv);
        tv = new TextView(this);
        tv.setText("Fractionné 1 : ");
        legende.addView(tv);
        tv = new TextView(this);
        tv.setText("Pit Stop : ");
        legende.addView(tv);
        tv = new TextView(this);
        tv.setText("Sprint 2 : ");
        legende.addView(tv);
        tv = new TextView(this);
        tv.setText("Fractionné 2 : ");
        legende.addView(tv);
        bigContainer.addView(legende);
        for(final Runner runner : runners){
            LinearLayout runnerLayout = new LinearLayout(this);
            runnerLayout.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(this);
            name.setText(" "+runner.getFistName()+" "+runner.getLastName());
            TextView sp1 = new TextView(this);
            TextView fr1 = new TextView(this);
            TextView sp = new TextView(this);
            TextView sp2 = new TextView(this);
            TextView fr2 = new TextView(this);
            for(final LapTime lapTime : runner.getLapTimesForRace(this,race)){
                sp1.append("-"+lapTime.getTimeSprint1()+"s-");
                fr1.append("-"+lapTime.getTimeFractionated1()+"s-");
                sp.append("-"+lapTime.getTimePitStop()+"s-");
                sp2.append("-"+lapTime.getTimeSprint2()+"s-");
                fr1.append("-"+lapTime.getTimeFractionated2()+"s-");
            }
            runnerLayout.addView(name);
            runnerLayout.addView(sp1);
            runnerLayout.addView(fr1);
            runnerLayout.addView(sp);
            runnerLayout.addView(sp2);
            runnerLayout.addView(fr2);
            bigContainer.addView(runnerLayout);
        }*/
    }

}
