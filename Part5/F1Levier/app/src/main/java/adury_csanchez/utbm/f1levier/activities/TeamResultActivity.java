package adury_csanchez.utbm.f1levier.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
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
        getSupportActionBar().setTitle(getResources().getString(R.string.results_of_team) + " \"" +team.getName()+"\"");

        LinearLayout bigContainer = findViewById(R.id.bigContainer);
        bigContainer.setOrientation(LinearLayout.VERTICAL);

        TextView tvBestSprint1 = null;
        long bestSprint1=Long.MAX_VALUE;

        TextView tvBestFractionned1 = null;
        long bestFractionned1=Long.MAX_VALUE;

        TextView tvBestSprint2 = null;
        long bestSprint2=Long.MAX_VALUE;

        TextView tvBestPitStop = null;
        long bestPitStop=Long.MAX_VALUE;

        TextView tvBestFractionned2 = null;
        long bestFractionned2=Long.MAX_VALUE;

        TextView tvBestGlobal = null;
        long bestGlobal=Long.MAX_VALUE;


        for(final Runner runner : runners) {

            // Runner Name
            TextView textViewRunnerName = new TextView(this);
            textViewRunnerName.setText(runner.getFistName() + " " + runner.getLastName() + " (" + runner.getWeight() + ")");
            bigContainer.addView(textViewRunnerName);
            //textViewRunnerName.setTextSize(Utils.mapPXtoDP(this, 6));
            textViewRunnerName.setGravity(Gravity.CENTER);
            //textViewRunnerName.setPadding(Utils.mapPXtoDP(this, 2), Utils.mapPXtoDP(this, 5), Utils.mapPXtoDP(this, 2), Utils.mapPXtoDP(this, 5));


            // Grid for results
            GridLayout grid = new GridLayout(this);
            grid.setColumnCount(7);

            // Set headers
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Lap)));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Sprint) + " 1"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Fract_)+ " 1"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Pit_stop)));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Sprint) + " 2"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Fract_)+ " 2"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Total)));

            // Arrays to compute averages
            List<Long> listTimeSprint1 = new ArrayList<>();
            List<Long> listTimeFractionned1 = new ArrayList<>();
            List<Long> listTimePitStop = new ArrayList<>();
            List<Long> listTimeSprint2 = new ArrayList<>();
            List<Long> listTimeFractionned2 = new ArrayList<>();
            List<Long> listTimeGlobal = new ArrayList<>();

            for (final LapTime lapTime : runner.getLapTimesForRace(this, race)) {

                // Lap number
                grid.addView(getTextViewNormalCell(Integer.toString(lapTime.getLapNumber())));

                TextView tv;

                // Sprint 1
                listTimeSprint1.add(lapTime.getTimeSprint1());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getTimeSprint1()));
                if(lapTime.getTimeSprint1() < bestSprint1) {
                    bestSprint1=lapTime.getTimeSprint1();
                    tvBestSprint1=tv;
                }
                grid.addView(tv);

                // Fractionned 1
                listTimeFractionned1.add(lapTime.getTimeFractionated1());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getTimeFractionated1()));
                if(lapTime.getTimeFractionated1() < bestFractionned1){
                    bestFractionned1=lapTime.getTimeFractionated1();
                    tvBestFractionned1=tv;
                }
                grid.addView(tv);

                // Pit stop
                listTimePitStop.add(lapTime.getTimePitStop());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getTimePitStop()));
                if(lapTime.getTimePitStop() < bestPitStop) {
                    bestPitStop=lapTime.getTimePitStop();
                    tvBestPitStop=tv;
                }
                grid.addView(tv);

                // Sprint 2
                listTimeSprint2.add(lapTime.getTimeSprint2());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getTimeSprint2()));
                if(lapTime.getTimeSprint2() < bestSprint2) {
                    bestSprint2=lapTime.getTimeSprint2();
                    tvBestSprint2=tv;
                }
                grid.addView(tv);

                // Fractionned 2
                listTimeFractionned2.add(lapTime.getTimeFractionated2());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getTimeFractionated2()));
                if(lapTime.getTimeFractionated2() < bestFractionned2){
                    bestFractionned2=lapTime.getTimeFractionated2();
                    tvBestFractionned2=tv;
                }
                grid.addView(tv);

                // Global
                listTimeGlobal.add(lapTime.getGlobalTime());
                tv=getTextViewNormalCell(Utils.formatTime(lapTime.getGlobalTime()));
                if(lapTime.getGlobalTime() < bestGlobal){
                    bestGlobal=lapTime.getGlobalTime();
                    tvBestGlobal=tv;
                }
                grid.addView(tv);
            }
            if(runner.getLapTimesForRace(this, race).size()>0){
                // Average
                grid.addView(getTextViewNormalCell(getResources().getString(R.string.Avg_)));

                // Average Sprint 1
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimeSprint1))));

                // Average Fractionned 1
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimeFractionned1))));

                // Average Pit stop
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimePitStop))));

                // Average Sprint 2
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimeSprint2))));

                // Average Fractionned 2
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimeFractionned2))));

                // Average Global
                grid.addView(getTextViewNormalCell(Utils.formatTime(Utils.average(listTimeGlobal))));
            }
            bigContainer.addView(grid);
        }

        if(tvBestSprint1 != null)tvBestSprint1.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        if(tvBestFractionned1!= null)tvBestFractionned1.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        if(tvBestPitStop != null)tvBestPitStop.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        if(tvBestSprint2 != null)tvBestSprint2.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        if(tvBestFractionned2 != null)tvBestFractionned2.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        if(tvBestGlobal != null)tvBestGlobal.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    TextView getTextViewHeaderCell(String text)
    {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(5,1,5,1);
        tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        return tv;
    }

    TextView getTextViewNormalCell(String text)
    {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(5,1,5,1);
        //tv.setPadding(Utils.mapPXtoDP(this,2),Utils.mapPXtoDP(this,2),Utils.mapPXtoDP(this,2),Utils.mapPXtoDP(this,2));
        tv.setBackgroundResource(R.layout.grid_items_border);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        return tv;
    }
}
