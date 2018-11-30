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
import android.widget.Spinner;
import android.widget.TextView;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.DataBase.ORM.DBTypeTour;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Temps;
import com.silentpangolin.codep25.Objects.TypeTour;

import java.util.ArrayList;

public class RankingPlayerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_player);

        initInstances();

        setSpinner();

        DBTemps dbTemps = new DBTemps(getApplicationContext());
        dbTemps.open();
        ArrayList<Temps> tps = dbTemps.getAllTemps();
        dbTemps.close();
        String txt = "";
        for(Temps t : tps)
            txt += t.toString() + "\n";
        ((TextView)findViewById(R.id.allTemps)).setText(txt);
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

        for(TypeTour t : allTypeTour)
            dataAdapterTour.add(t.getName_typetour());

        dataAdapterTour.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTour.setAdapter(dataAdapterCoureur);
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

        for(Coureur c : allcrrs)
            dataAdapterCoureur.add(c.getNom_crr() + " " + c.getPrenom_crr());

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


    private void setList(){}

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
