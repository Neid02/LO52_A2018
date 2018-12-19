package adury_csanchez.utbm.f1levier.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.xml.datatype.Duration;

import adury_csanchez.utbm.f1levier.DAO.RaceDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.LapTime;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;
import adury_csanchez.utbm.f1levier.model.TeamTimeComparator;
import adury_csanchez.utbm.f1levier.utils.Utils;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Button> listTriggerButtons;
    private Race race;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Long raceID = getIntent().getExtras().getLong("RaceID");
        race = new RaceDAO(this).getRaceById(raceID);

        // Set action bar title
        getSupportActionBar().setTitle(R.string.results);

        List<Team> lt = race.getTeams(this);
        LinearLayout linearLayoutContainer = findViewById(R.id.myLayout);
        Collections.sort(lt,new TeamTimeComparator(this));
        LinearLayout linearLayoutRowl = new LinearLayout(this);
        linearLayoutRowl.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutRowl.setGravity(Gravity.CENTER);
        TextView placel = new TextView(this);
        placel.setGravity(Gravity.CENTER);
        placel.setWidth(Utils.mapPXtoDP(this,90));
        placel.append("Classement");
        linearLayoutRowl.addView(placel);
        TextView teaml = new TextView(this);
        teaml.setGravity(Gravity.CENTER);
        teaml.setWidth(Utils.mapPXtoDP(this,150));
        teaml.append("Equipe");
        linearLayoutRowl.addView(teaml);
        TextView timel = new TextView(this);
        timel.append("Chrono");
        timel.setGravity(Gravity.CENTER);
        timel.setWidth(Utils.mapPXtoDP(this,90));
        linearLayoutRowl.addView(timel);
        linearLayoutContainer.addView(linearLayoutRowl);
        Integer i = 1;
        for(final Team team : lt) {
            // Create a ROW linearLayout (Horizontal)
            LinearLayout linearLayoutRow = new LinearLayout(this);
            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutRow.setGravity(Gravity.CENTER);
            // Add it to the linearLayoutContainer

            TextView place = new TextView(this);
            place.setGravity(Gravity.CENTER);
            place.setWidth(Utils.mapPXtoDP(this,90));
            place.append(""+i);
            linearLayoutRow.addView(place);
            Button triggerButton = new Button(this);
            triggerButton.setText(" "+team.getName());
            triggerButton.setWidth(Utils.mapPXtoDP(this,150));
            triggerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResultActivity.this, TeamResultActivity.class);
                    intent.putExtra("RaceID",new Long(race.getId()));
                    intent.putExtra("TeamID",new Long(team.getId()));
                    startActivity(intent);
                }
            });
            linearLayoutRow.addView(triggerButton);
            TextView time = new TextView(this);
            time.append(""+Utils.formatTime(team.getGlobalTime(this)));
            time.setGravity(Gravity.CENTER);
            time.setWidth(Utils.mapPXtoDP(this,90));
            linearLayoutRow.addView(time);
            linearLayoutContainer.addView(linearLayoutRow);
            i++;
        }
    }
}
