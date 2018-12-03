package com.silentpangolin.codep25;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.DataBase.ORM.DBTypeTour;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Temps;
import com.silentpangolin.codep25.Objects.TypeTour;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RankingPlayerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private int[] coureurs;
    private int[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_player);

        initInstances();

        setSpinner();
    }


    private void setSpinner(){
        final Spinner spinnerTour = (Spinner) findViewById(R.id.spinnerTour);
        final Spinner spinnerCoureur = (Spinner) findViewById(R.id.spinnerCoureur);
        ArrayAdapter<String> dataAdapterTour = new ArrayAdapter<>(this, R.layout.spinner_item);
        ArrayAdapter<String> dataAdapterCoureur = new ArrayAdapter<>(this, R.layout.spinner_item);

        DBTypeTour dbTypeTour = new DBTypeTour(getApplicationContext());
        dbTypeTour.open();
        ArrayList<TypeTour> allTypeTour = dbTypeTour.getAllTypeTour();
        dbTypeTour.close();

        types = new int[allTypeTour.size()];
        for (int i = 0; i < allTypeTour.size(); ++i) {
            dataAdapterTour.add(allTypeTour.get(i).getName_typetour());
            types[i] = allTypeTour.get(i).getId_typetour();
        }

        dataAdapterTour.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTour.setAdapter(dataAdapterTour);
        spinnerTour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
        dbCoureur.open();
        ArrayList<Coureur> allcrrs = dbCoureur.getAllCoureur();
        dbCoureur.close();

        coureurs = new int[allcrrs.size()];
        for (int i = 0; i < allcrrs.size(); ++i) {
            dataAdapterCoureur.add(allcrrs.get(i).getNom_crr() + " " + allcrrs.get(i).getPrenom_crr());
            coureurs[i] = allcrrs.get(i).getId_crr();
        }

        dataAdapterCoureur.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerCoureur.setAdapter(dataAdapterCoureur);
        spinnerCoureur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setList(){
        Spinner coureur = (Spinner) findViewById(R.id.spinnerCoureur);
        Spinner type = (Spinner) findViewById(R.id.spinnerTour);
        DBTemps dbTemps = new DBTemps(getApplicationContext());
        dbTemps.open();
        ArrayList<Temps> tps = dbTemps.getAllTempsWithIDCoureurAndIDType(coureurs[coureur.getSelectedItemPosition()], types[type.getSelectedItemPosition()]);
        dbTemps.close();

        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
        ListView listRank = (ListView) findViewById(R.id.listRank);

        if (tps != null) {
            Log.wtf("DisplayError", Integer.toString(tps.size()));
            if (tps.size() > 0) {
                for (int i = 0; i < tps.size(); ++i)
                    listItem.add(getItem(tps.get(i).getDuree_temps(), i + 1));

                SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_ranking_coureur,
                        new String[]{"numRank", "dureeRank"}, new int[]{R.id.numRank, R.id.dureeRank});

                listRank.setAdapter(adapter);
            }
        }
    }

    private HashMap<String, String> getItem(long duree, int i) {
        HashMap<String, String> item = new HashMap<>();
        item.put("numRank", Integer.toString(i));
        item.put("dureeRank", getTime(duree));
        return item;
    }

    private String getTime(long duree){
        String time = "";
        time = Long.toString(duree % 1000);
        duree /= 1000;
        time = Long.toString(duree % 60) + " . " + time;
        duree /= 60;
        time = Long.toString(duree) + " : " + time;
        return time;
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(RankingPlayerActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(RankingPlayerActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.player:
                        startActivity(new Intent(RankingPlayerActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(RankingPlayerActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(RankingPlayerActivity.this, RankingPlayerActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(RankingPlayerActivity.this, MainActivity.class));
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
            startActivity(new Intent(RankingPlayerActivity.this, CreditsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
