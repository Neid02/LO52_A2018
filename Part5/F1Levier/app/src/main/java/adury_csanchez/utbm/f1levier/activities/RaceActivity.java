package adury_csanchez.utbm.f1levier.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import adury_csanchez.utbm.f1levier.DAO.EnrolmentDAO;
import adury_csanchez.utbm.f1levier.DAO.LapTimeDAO;
import adury_csanchez.utbm.f1levier.DAO.RaceDAO;
import adury_csanchez.utbm.f1levier.DAO.RunnerDAO;
import adury_csanchez.utbm.f1levier.DAO.SubscriptionDAO;
import adury_csanchez.utbm.f1levier.DAO.TeamDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.Enrolment;
import adury_csanchez.utbm.f1levier.model.LapTime;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;
import adury_csanchez.utbm.f1levier.model.TeamWeightComparator;

public class RaceActivity extends AppCompatActivity {

   private ArrayList<Button> listTriggerButtons;
   private Race race;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);


        //=================================================================
        //              Chronometer AND start/stop button creation
        //=================================================================

        // Retrieve chronometer from XML file
        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);

        // Retrieve START/STOP button from XML file
        final Button buttonChronometer = (Button) findViewById(R.id.buttonChronometer);
        buttonChronometer.setText("START");
        buttonChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If started
                if(buttonChronometer.getText()=="START"){

                    // Set stop
                    buttonChronometer.setText("STOP");
                    Drawable rightButtonIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_pause);
                    buttonChronometer.setCompoundDrawablesWithIntrinsicBounds(null,null , rightButtonIcon, null);

                    // Enable all buttons
                    for (Button b : listTriggerButtons)
                        b.setEnabled(true);

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                }
                // If stoped
                else
                {
                    chronometer.stop();
                    // Go to result activity
                    goToResults();
                }
            }
        });

        //=================================================================
        //              Data generation
        //=================================================================

        // TODO  =====  <  To remove once first activity is done
        this.deleteDatabase("f1levier.db");
        race = new RaceDAO(this).createRace("Deleon race");


        int nbRunnersToGenerate=5;
        createWeightedTeamsForRace(race,createRandomRunners(nbRunnersToGenerate));

        // TODO To remove \> =====================================




        final List<Team> listTeams = race.getTeams(this);
        final LapTimeDAO lapTimeDAO=new LapTimeDAO(this);

        //====================================================================
        //              Rows :
        // - Button to trigger the chronometer
        // - Progress bar to display the current lap progression
        // - Individuals progress bar to display the progression of each runners
        //====================================================================

        LinearLayout linearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout);

        listTriggerButtons = new ArrayList<Button>();

        final AtomicInteger nbTeamFinished = new AtomicInteger(0);

        for(final Team team : listTeams){
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
            triggerButton.setText(team.getName()+" "+team.getWeight(this));
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


            // Create Progress bar to display current lap progression
            final ProgressBar progressBarCurrentLap=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
            //progressBarCurrentLap.setProgressDrawable(new SegmentedProgressDrawable(0xff195681, 0x32123346));
            progressBarCurrentLap.setProgressDrawable(new SegmentedProgressDrawable(0xff195681, 0x64123346));
            progressBarCurrentLap.setMax(5);
            progressBarCurrentLap.setProgress(0);
            progressBarCurrentLap.setPadding(0,0,0,0);
            progressBarCurrentLap.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

            // Create a frame layout for the progressbar + labels on overlay
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayoutSub.addView(frameLayout);

            // Create a grid layout to hold the 5 labels (Sprint, Fractionned, Pit Stop, Sprint, Fractionned)
            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setColumnCount(5);
            gridLayout.setRowCount(1);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            gridLayout.setForegroundGravity(Gravity.CENTER);

            List<String> list = Arrays.asList("Sprint", "Frac.", "Pit Stop", "Sprint", "Frac.");
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

            frameLayout.addView(progressBarCurrentLap);
            frameLayout.addView(gridLayout);


            // Create a SUB SUB linearLayout (Horizontal)
            LinearLayout linearLayoutSubSub=new LinearLayout(this);
            linearLayoutSubSub.setGravity(Gravity.CENTER_VERTICAL);
            linearLayoutSubSub.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutSubSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            // Add it to the linearLayoutSub
            linearLayoutSub.addView(linearLayoutSubSub);

            // Get runners enrolled in this team
            List<Runner> runners = team.getRunners(this);

            final List<ProgressBar> listProgressBarIndividual = new ArrayList<ProgressBar>();
            final List<LapTime> listCurrentLaps = new ArrayList<LapTime>();

            for(Runner runner : runners)
            {
                // Create a text view with the name of the runner
                TextView textViewRunnerLabel = new TextView(this);
                textViewRunnerLabel.setText(runner.getFistName());
                textViewRunnerLabel.setPadding(20,0,20,0);
                // Add it to the linearLayoutSubSub
                linearLayoutSubSub.addView(textViewRunnerLabel);

                // Compute the number of laps each runners will have to run according to the team size
                int nbLapsToRunForEachRunner =  runners.size()==3 ? 2 : 3;

                // Create a progressbar to display individual progression of each runners
                final ProgressBar progressBarIndividual=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
                progressBarIndividual.setProgressDrawable(new SegmentedProgressDrawable(0xff1f6790, 0x32123346, nbLapsToRunForEachRunner));
                progressBarIndividual.setMax(nbLapsToRunForEachRunner);
                progressBarIndividual.setProgress(0);
                progressBarIndividual.setPadding(0,10,0,10);
                // Calculate and apply progressBar size according to the number of runner in the current team
                progressBarIndividual.setLayoutParams(new LinearLayout.LayoutParams(nbLapsToRunForEachRunner * mapPXtoDP(10),LinearLayout.LayoutParams.WRAP_CONTENT));

                // Add it to the linearLayoutSubSub
                linearLayoutSubSub.addView(progressBarIndividual);

                // Add it to a list
                listProgressBarIndividual.add(progressBarIndividual);

                LapTime lapTime = new LapTime();
                lapTime.setAuthor(runner);
                listCurrentLaps.add(lapTime);
            }
            final AtomicLong previousTime= new AtomicLong(SystemClock.elapsedRealtime() - chronometer.getBase());
            triggerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    List<Integer> listProgressOnEachBars= new ArrayList<Integer>();

                    int lapNumber=1;
                    for(ProgressBar p : listProgressBarIndividual)
                    {
                        int individualProgress = p.getProgress();
                        lapNumber+=individualProgress;
                        listProgressOnEachBars.add(individualProgress);
                    }

                    int lapProgress=progressBarCurrentLap.getProgress() + 1;
                    if(lapProgress >5)
                        lapProgress=1;

                    // Get the index of the minimum value the most on the left
                    int runnerIndex = listProgressOnEachBars.indexOf(Collections.min(listProgressOnEachBars));

                    // We can get the current lap
                    LapTime currentLap = listCurrentLaps.get(runnerIndex);

                    // And the individual progressbar
                    ProgressBar progressBarIndividual= listProgressBarIndividual.get(runnerIndex);

                    // Get the elapsed time
                    long elapsedMillis = SystemClock.elapsedRealtime()-chronometer.getBase() - previousTime.get();

                    switch (lapProgress)
                    {
                        case 1:
                            currentLap.setTimeSprint1(elapsedMillis);
                            break;
                        case 2:
                            currentLap.setTimeFractionated1(elapsedMillis);
                            break;
                        case 3:
                            currentLap.setTimePitStop(elapsedMillis);
                            break;
                        case 4:
                            currentLap.setTimeSprint2(elapsedMillis);
                            break;
                        case 5:
                            currentLap.setTimeFractionated2(elapsedMillis);
                            currentLap.setLapNumber(lapNumber);

                            // Add lap time to database
                            lapTimeDAO.createLapTime(
                                    currentLap.getAuthor().getId(),
                                    race.getId(),
                                    currentLap.getLapNumber(),
                                    currentLap.getTimeSprint1(),
                                    currentLap.getTimeFractionated1(),
                                    currentLap.getTimePitStop(),
                                    currentLap.getTimeSprint2(),
                                    currentLap.getTimeFractionated2());

                            // Increment individual progress
                            progressBarIndividual.setProgress(progressBarIndividual.getProgress()+1);
                            break;
                    }

                    // Increment current lap progression on the progress bar
                    progressBarCurrentLap.setProgress(lapProgress);

                    // If all the runners of the time have finished
                    if(lapNumber==6 && lapProgress==5)
                    {
                        // Disable the button of the team
                        listTriggerButtons.get(listTeams.indexOf(team)).setEnabled(false);

                        // Check if everyone have finished
                        if(nbTeamFinished.incrementAndGet() >= listTeams.size()){
                            // Start the result activity
                            goToResults();
                        }
                    }
                    previousTime.set(elapsedMillis + previousTime.get());
                }
            });

        }
    }
    public List<Runner> createRandomRunners(int nb){
        RunnerDAO runnerDAO = new RunnerDAO(this);
        Random rd = new Random();
        List<Runner> lr = new ArrayList<>();
        for(int i = 0;i<nb;i++) {
            Runner runner = runnerDAO.createRunner(RandomNames.getRandomFirstName(),RandomNames.getRandomLastName(),rd.nextInt(100));
            lr.add(runner);
        }
        return lr;
    }
    public void createWeightedTeamsForRace(Race race, List<Runner> lr){
        TeamDAO teamDAO = new TeamDAO(this);
        EnrolmentDAO enrolmentDAO = new EnrolmentDAO(this);
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO(this);
        Collections.sort(lr);
        Collections.reverse(lr);
        int nbTeams = (lr.size()+2)/3;
        int nbTeamsOf2 = nbTeams*3-lr.size();
        for(int i = 0;i<nbTeams;i++){
            Team t = teamDAO.createTeam(RandomNames.getRandomCountry());
            subscriptionDAO.createSubscription(t.getId(),race.getId());
        }
        Iterator<Runner> it = lr.iterator();
        List<Team> lt = teamDAO.getTeamsOfRace(race.getId());
        for(int i = 0;i<nbTeams;i++){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
        for(int i = nbTeams-1;i>=0;i--){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
        Collections.sort(lt,new TeamWeightComparator(this));
        for(int i = 0;i<nbTeams-nbTeamsOf2;i++){
            if(it.hasNext()) enrolmentDAO.createEnrolment(it.next().getId(),lt.get(i).getId());
        }
    }

    public int mapPXtoDP(int dimensionInPx){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPx, getResources().getDisplayMetrics()));
    }
    public void goToResults(){
        Intent intent = new Intent(RaceActivity.this, ResultActivity.class);
        intent.putExtra("RaceID",new Long(race.getId()));
        startActivity(intent);
    }
}