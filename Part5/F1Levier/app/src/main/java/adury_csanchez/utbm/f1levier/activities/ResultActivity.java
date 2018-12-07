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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        race = new RaceDAO(this).getRaceById(getIntent().getExtras().getLong("RaceID"));
        List<Team> lt = race.getTeams(this);
        //Collections.sort(lt,new TeamTimeComparator(this));
        //Collections.reverse(lt);
        setContentView(R.layout.activity_result);LinearLayout linearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout);
        Integer i = 0;
        for(final Team team : lt){
            // Create a ROW linearLayout (Horizontal)
            LinearLayout linearLayoutRow=new LinearLayout(this);
            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutRow.setGravity(Gravity.CENTER);
            linearLayoutRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayoutRow.setPadding(0,10,0,10);
            // Add it to the linearLayoutContainer
            linearLayoutContainer.addView(linearLayoutRow);

            // Create button
            Button triggerButton = new Button(this);
            triggerButton.setText(team.getName());
            triggerButton.setEnabled(false);
            triggerButton.setWidth(mapPXtoDP(100));
            // Add it to the linearLayoutRow and to the list of trigger buttons
            linearLayoutRow.addView(triggerButton);
            listTriggerButtons.add(triggerButton);


            // Create a SUB linearLayout  (Vertical)
            LinearLayout linearLayoutSub=new LinearLayout(this);
            linearLayoutSub.setOrientation(LinearLayout.VERTICAL);
            linearLayoutSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            // Add it to the linearLayoutRow
            linearLayoutRow.addView(linearLayoutSub);

            // Create a grid layout to hold the infos
            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setColumnCount(5);
            gridLayout.setRowCount(1);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            gridLayout.setForegroundGravity(Gravity.CENTER);

            List<String> list = Arrays.asList("Place : ", i.toString(), "Temps : ", Double.toString(team.getGlobalTime(this)));
            for(String s : list)
            {
                TextView textViewLegend = new TextView(this);
                textViewLegend.setText(s);
                textViewLegend.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textViewLegend.setTypeface(Typeface.DEFAULT_BOLD);
                textViewLegend.setTextColor(Color.WHITE);
                gridLayout.addView(textViewLegend);
                GridLayout.LayoutParams paramsLegent= new GridLayout.LayoutParams(GridLayout.spec(
                        GridLayout.UNDEFINED,GridLayout.FILL,1f),
                        GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f));
                paramsLegent.setGravity(Gravity.CENTER);
                textViewLegend.setLayoutParams(paramsLegent);
            }



            triggerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }

            });
            i++;

        }




    }
    public int mapPXtoDP(int dimensionInPx){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPx, getResources().getDisplayMetrics()));
    }
}
