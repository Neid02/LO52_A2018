package com.silentpangolin.codep25;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.support.design.widget.NavigationView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Equipe;
import com.silentpangolin.codep25.Objects.ShakeDetector;
import com.jetradarmobile.snowfall.SnowfallView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    /*private ArrayList<HashMap<String, String>> laps;
    private ListView lapsListView;
    private int numLaps = 0;
    private SimpleAdapter adapter;*/
    private ArrayList<Coureur> coureurs;
    private ArrayList<Equipe> equipes;
    private boolean OnStop = true;
    private long timeWhenPaused = 0;
    private int maxTeam = 0;
    private int ordrePassage = 0;
    private ArrayList<Button> allButtons = new ArrayList<Button>();
    private int[] steps;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initInstances();

            getDataFromDataBase();

            setSpinner();

            setButton();

            initShaker();
        }catch(Exception e){
            Log.e("MyApplication", e.getMessage());
        }

        /*laps = new ArrayList<>();
        lapsListView = findViewById(R.id.listLaps);
        adapter = new SimpleAdapter(this.getBaseContext(), laps, R.layout.list_laps,
                new String[]{"num", "time"}, new int[]{R.id.numLap, R.id.timeLap});
        lapsListView.setAdapter(adapter);*/

    }


    public AlertDialog onCreateDialog() {
        final ArrayList<Equipe> mSelectedItems = new ArrayList<Equipe>();
        final String[] nameTeam = new String[equipes.size()];
        for(int i = 0; i < equipes.size(); ++i){
            nameTeam[i] = equipes.get(i).getName_equ();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.chooseTeam)
                .setMultiChoiceItems(nameTeam, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(equipes.get(which));
                                } else if (mSelectedItems.contains(equipes.get(which))) {
                                    mSelectedItems.remove(equipes.get(which));
                                }
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setTableLayoutButton(mSelectedItems);
                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    private void setTableLayoutButton(@NotNull ArrayList<Equipe> teamSelected){
        final TableLayout tableLayoutButton = (TableLayout) findViewById(R.id.tableLayoutButton);
        allButtons = new ArrayList<Button>();
        tableLayoutButton.removeAllViews();
        final ArrayList<Coureur> crrs = new ArrayList<Coureur>();
        ArrayList<String> names = new ArrayList<String>();
        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
        for(Equipe e : teamSelected){
            Coureur c = dbCoureur.getCoureurWithIDTeamAndOrder(e.getId_equ(), ordrePassage);
            if(c != null){
                crrs.add(c);
                names.add(e.getName_equ());
            }
        }
        dbCoureur.close();
        steps = new int[crrs.size()];
        TableRow tableRow = new TableRow(getApplicationContext());
        tableRow.setGravity(Gravity.CENTER);
        for(int i = 0; i < crrs.size(); ++i){
            steps[i] = 0;
            final Button button = new Button(getApplicationContext());
            final int pos = i;
            allButtons.add(button);
            button.setText(names.get(i) + " \n " + crrs.get(i).getPrenom_crr() + " " + crrs.get(i).getNom_crr());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** MAKE THE ARRAY LIST OF STRING
                     * FOR INSERTIONIN DATABASE */
                    ++steps[pos];
                    Log.e("DisplayTime",
                            "ID CRR : " + crrs.get(pos).getId_crr() +
                                    " - DUREE : " + getTime() +
                                    " - ETAPE : " + steps[pos]);
                    if(steps[pos] == 5){
                        button.setClickable(false);
                        button.setAlpha(0.5f);
                    }
                    boolean flag = true;
                    for(int i = 0; i < crrs.size(); ++i)
                        if(steps[i] != 5)
                            flag = false;
                    if(flag){
                        /** HERE INSERT TEMPS IN DATABASE
                         * ARRAY LIST OF STRING
                         * + SAY "IT'S GOOD BOY, IT IS IN THE DATABASE" */
                        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        timeWhenPaused = 0;
                        OnStop = !OnStop;
                        changeButtons(OnStop);
                        tableLayoutButton.removeAllViews();
                    }

                }
            });
            button.setClickable(false);
            button.setFocusable(false);
            button.setAlpha(0.5f);

            if (i % 2 == 0) {
                tableRow = new TableRow(getApplicationContext());
                tableRow.setGravity(Gravity.CENTER);
                tableRow.addView(button, i % 2);
                if (i == (crrs.size() - 1)) tableLayoutButton.addView(tableRow);
            }else {
                tableRow.addView(button, i % 2);
                tableLayoutButton.addView(tableRow);
            }
        }
    }

    private void easterEgg() {
        final SnowfallView snowfall = (SnowfallView) findViewById(R.id.snowfall);
        final RelativeLayout sapin = (RelativeLayout) findViewById(R.id.sapin);
        final RelativeLayout guirlande = (RelativeLayout) findViewById(R.id.guirlande);
        sapin.bringToFront();
        guirlande.bringToFront();
        snowfall.bringToFront();
        sapin.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        snowfall.setVisibility(View.VISIBLE);
        guirlande.setVisibility(View.VISIBLE);
        snowfall.restartFalling();
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(200);
        animation.setStartOffset(0);
        animation.setFillAfter(true);
        sapin.startAnimation(animation);
        guirlande.startAnimation(animation);
        Runnable myrunnable = new Runnable() {
            public void run() {
                AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(1000);
                animation.setStartOffset(4500);
                animation.setFillAfter(true);
                sapin.startAnimation(animation);
                guirlande.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        snowfall.stopFalling();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        sapin.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        };
        Handler h = new Handler();
        h.postDelayed(myrunnable, 5000);
    }

    private void initShaker(){
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                easterEgg();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void getDataFromDataBase(){
        DBEquipe dbEquipe = new DBEquipe(this);
        dbEquipe.open();
        equipes = dbEquipe.getAllEquipe();
        dbEquipe.close();

        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
        coureurs = dbCoureur.getAllCoureur();
        maxTeam = dbCoureur.getNumMaxCoureurByTeam();
        dbCoureur.close();
    }

    private void changeButtons(boolean OnStop){
        Button start_pause = (Button) findViewById(R.id.start_pause);
        Button reset_lap = (Button) findViewById(R.id.reset_lap);

        if(OnStop){
            reset_lap.setAlpha(1.0f);
            reset_lap.setClickable(true);
            reset_lap.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.reset), null, null, null);
            reset_lap.setText(R.string.reset);
            start_pause.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.start), null, null, null);
            start_pause.setText(R.string.start);
            if(allButtons.size() != 0)
                for(Button b : allButtons) {
                    b.setClickable(false);
                    b.setAlpha(0.5f);
                }
        }else{
            start_pause.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.stop), null, null, null);
            start_pause.setText(R.string.pause);
            reset_lap.setAlpha(0.5f);
            reset_lap.setClickable(false);
            if(allButtons.size() != 0)
                for(Button b : allButtons) {
                    b.setClickable(true);
                    b.setAlpha(1.0f);
                }
        }
    }

    private long getTime(){
        return SystemClock.elapsedRealtime() - ((Chronometer)findViewById(R.id.chronometer)).getBase();
    }

    private void setButton(){
        Button start_pause = (Button) findViewById(R.id.start_pause);
        Button reset_lap = (Button) findViewById(R.id.reset_lap);
        Button chooseTeam = (Button) findViewById(R.id.chooseTeam);
        ImageView dropDown = (ImageView) findViewById(R.id.dropdown);
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    LinearLayout linear = (LinearLayout) findViewById(R.id.display);
                    ViewGroup.LayoutParams layoutParams = linear.getLayoutParams();
                    if ((layoutParams.height == 0) && (layoutParams.width == 0)) {
                        linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    } else {
                        linear.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                    }
            }
        });

        chooseTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = onCreateDialog();
                alertDialog.show();
            }
        });
        changeButtons(OnStop);
        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnStop) {
                    chronometer.start();
                    chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenPaused);
                }else {
                    timeWhenPaused = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                }
                OnStop = !OnStop;
                changeButtons(OnStop);
            }
        });

        reset_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnStop) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    timeWhenPaused = 0;
                    if(steps.length != 0)
                        for(int i = 0; i < steps.length; ++i)
                            steps[i] = 0;
                    /*numLaps = 0;
                    laps.clear();
                    adapter.notifyDataSetChanged();
                }else{
                    // TOUR ICI
                    long millis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    HashMap<String, String> item = new HashMap<>();
                    numLaps++;
                    item.put("num", Integer.toString(numLaps));
                    String msm = String.format(Locale.FRANCE, "%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
                            millis);
                    item.put("time", msm);

                    laps.add(item);
                    Toast.makeText(getApplicationContext(), Long.toString(millis), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();*/
                }
            }
        });
    }

    private void setSpinner(){
        final Spinner spinnerOrdre = (Spinner) findViewById(R.id.ordrePassage);
        ArrayAdapter<String> dataAdapterOrdre = new ArrayAdapter<>(this, R.layout.spinner_item);

        for(int i = 1; i <= maxTeam; ++i){
            dataAdapterOrdre.add(Integer.toString(i));
        }
        dataAdapterOrdre.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerOrdre.setAdapter(dataAdapterOrdre);
        spinnerOrdre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ordrePassage = ++i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.player:
                        startActivity(new Intent(MainActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(MainActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    /** SETTINGS */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_credits) {
            startActivity(new Intent(MainActivity.this, CreditsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
