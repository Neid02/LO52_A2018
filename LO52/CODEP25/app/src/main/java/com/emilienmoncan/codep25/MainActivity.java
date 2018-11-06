package com.emilienmoncan.codep25;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.emilienmoncan.codep25.DataBase.ORM.DBCoureur;
import com.emilienmoncan.codep25.DataBase.ORM.DBEquipe;
import com.emilienmoncan.codep25.Objects.Coureur;
import com.emilienmoncan.codep25.Objects.Equipe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private long milliseconds = 0;
    private long seconds = 0;
    private long minutes = 0;
    private long time = 0;
    private static Timer timer;
    private TextView displayTimer;
    private boolean isStopped;
    private ArrayList<Coureur> coureurs;
    private ArrayList<Equipe> equipes;
    private int idCoureur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        getDataFromDataBase();

        setSpinners();

        setButton();
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

    private void displayTime(long time){
        milliseconds = time % 1000;
        time /= 1000;
        seconds = time % 60;
        time /= 60;
        minutes = time;
        String theTime;
        if (minutes < 10)
            theTime = "0" + minutes + " : ";
        else
            theTime = minutes + " : ";
        if (seconds < 10)
            theTime += "0" + seconds + " : ";
        else
            theTime += seconds + " : ";
        if (milliseconds < 100)
            theTime += "0";
        if(milliseconds < 10)
            theTime += "0";
        theTime += milliseconds;
        displayTimer.setText(theTime);
    }

    private void setButton(){
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minutes = seconds = milliseconds = 0;
                timer = new Timer();
                isStopped = false;
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!(isStopped)){
                                    time += 1;
                                    displayTime(time);
                                }
                            }
                        });
                    }
                }, 0, 1);
            }
        });

        Button tour = (Button) findViewById(R.id.tour);
        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), Long.toString(time), Toast.LENGTH_SHORT).show();
                time = 0;
                displayTime(0);
            }
        });

        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                timer.purge();
                milliseconds = seconds = minutes = time = 0;
                isStopped = true;
                displayTime(0);
            }
        });
    }

    private void setSpinners(){
        final Spinner spinnerEquipe, spinnerCoureur;
        displayTimer = (TextView) findViewById(R.id.displayTimer);
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
