package adury_csanchez.utbm.f1levier.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
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

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Button> listTriggerButtons;
    private Race race;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Long raceID = getIntent().getExtras().getLong("RaceID");
        race = new RaceDAO(this).getRaceById(raceID);
        List<Team> lt = race.getTeams(this);
        LinearLayout linearLayoutContainer = findViewById(R.id.myLayout);
        Collections.sort(lt,new TeamTimeComparator(this));

        Integer i = 1;
        for(final Team team : lt) {
            // Create a ROW linearLayout (Horizontal)
            LinearLayout linearLayoutRow = new LinearLayout(this);
            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutRow.setGravity(Gravity.CENTER);
            // Add it to the linearLayoutContainer

            TextView place = new TextView(this);
            place.append("Place : "+i);
            linearLayoutRow.addView(place);
            Button triggerButton = new Button(this);
            triggerButton.setText(" "+team.getName());
            linearLayoutRow.addView(triggerButton);
            TextView time = new TextView(this);
            time.append("Chrono : "+team.getGlobalTime(this)/1000.0+"s");
            linearLayoutRow.addView(time);
            linearLayoutContainer.addView(linearLayoutRow);
            i++;
        }
    }
    public int mapPXtoDP(int dimensionInPx){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPx, getResources().getDisplayMetrics()));
    }
}
