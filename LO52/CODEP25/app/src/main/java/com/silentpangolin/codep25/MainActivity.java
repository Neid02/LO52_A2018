package com.silentpangolin.codep25;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.support.design.widget.NavigationView;
import android.widget.Toast;
import android.widget.ListView;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Equipe;
import com.silentpangolin.codep25.Objects.ShakeDetector;
import com.jetradarmobile.snowfall.SnowfallView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private ArrayList<HashMap<String, String>> laps;
    private ListView lapsListView;
    private int numLaps = 0;
    private ArrayList<Coureur> coureurs;
    private ArrayList<Equipe> equipes;
    private int idCoureur;
    private boolean OnStop = true;
    private long timeWhenPaused = 0;
    private SimpleAdapter adapter;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        getDataFromDataBase();

        setSpinners();

        setButton();

        initShaker();

        laps = new ArrayList<>();
        lapsListView = findViewById(R.id.listLaps);
        adapter = new SimpleAdapter(this.getBaseContext(), laps, R.layout.list_laps,
                new String[]{"num", "time"}, new int[]{R.id.numLap, R.id.timeLap});
        lapsListView.setAdapter(adapter);
    }

    private void easterEgg(int count) {
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
                easterEgg(count);
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
        dbCoureur.close();
    }

    private void changeButtons(boolean OnStop){
        Button start_pause = (Button) findViewById(R.id.start_pause);
        Button reset_lap = (Button) findViewById(R.id.reset_lap);

        if(OnStop){
            reset_lap.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.reset), null, null, null);
            reset_lap.setText(R.string.reset);
            start_pause.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.start), null, null, null);
            start_pause.setText(R.string.start);
        }else{
            reset_lap.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.lap), null, null, null);
            reset_lap.setText(R.string.lap);
            start_pause.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.stop), null, null, null);
            start_pause.setText(R.string.pause);
        }
    }

    private void setButton(){
        Button start_pause = (Button) findViewById(R.id.start_pause);
        Button reset_lap = (Button) findViewById(R.id.reset_lap);
        changeButtons(OnStop);
        final Chronometer chronometer = (Chronometer)findViewById(R.id.chronometer);
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
                    numLaps = 0;
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
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void setSpinners(){
        final Spinner spinnerEquipe, spinnerCoureur;
        spinnerEquipe = (Spinner) findViewById(R.id.equipes);
        spinnerCoureur = (Spinner) findViewById(R.id.coureurs);

        ArrayAdapter<String> dataAdapterEquipe = new ArrayAdapter<>(this, R.layout.spinner_item);
        if (equipes.size() > 0)
            for(Equipe e : equipes)
                dataAdapterEquipe.add(e.getName_equ());

        dataAdapterEquipe.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerEquipe.setAdapter(dataAdapterEquipe);

        if(coureurs.size() > 0)
            spinnerCoureur.setAdapter(getAdapterForSpinnerCoureur(0));

        spinnerEquipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (coureurs.size() > 0)
                    spinnerCoureur.setAdapter(getAdapterForSpinnerCoureur(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCoureur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                idCoureur = coureurs.get(position).getId_crr();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayAdapter getAdapterForSpinnerCoureur(int position){
        ArrayAdapter<String>  dataAdapterCoureur = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item);
        if (coureurs.size() > 0){
            DBCoureur dbCoureur = new DBCoureur(this);
            dbCoureur.open();
            ArrayList<Coureur> crrs = dbCoureur.getAllCoureurWithIDTeam(equipes.get(position).getId_equ());
            dbCoureur.close();
            for(Coureur c : crrs)
                dataAdapterCoureur.add(c.getNom_crr().toUpperCase() + " " + c.getPrenom_crr());
        }

        dataAdapterCoureur.setDropDownViewResource(R.layout.spinner_dropdown_item);
        return dataAdapterCoureur;
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
