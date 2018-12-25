package com.silentpangolin.codep25;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.DataBase.ORM.DBTypeTour;
import com.silentpangolin.codep25.Objects.Temps;
import com.silentpangolin.codep25.Objects.TypeTour;

import java.util.ArrayList;
import java.util.HashMap;

public class RankingTeamActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private String[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_team);

        initInstances();

        setSpinner();
    }

    private void setSpinner(){
        final Spinner spinnerTourTeam = (Spinner) findViewById(R.id.spinnerTourTeam);
        ArrayAdapter<String> dataAdapterTourTeam = new ArrayAdapter<>(this, R.layout.spinner_item);

        DBTypeTour dbTypeTour = new DBTypeTour(getApplicationContext());
        dbTypeTour.open();
        ArrayList<TypeTour> allTypeTour = dbTypeTour.getAllTypeTour();
        dbTypeTour.close();

        types = new String[allTypeTour.size() * 2];
        for (int i = 0; i < allTypeTour.size(); ++i) {
            dataAdapterTourTeam.add(allTypeTour.get(i).getName_typetour());
            types[i] = allTypeTour.get(i).getInitials_typetour();
        }
        for (int i = 0; i < allTypeTour.size(); ++i){
            dataAdapterTourTeam.add("Moyenne " + allTypeTour.get(i).getName_typetour());
            types[i + allTypeTour.size()] = "m" + allTypeTour.get(i).getInitials_typetour();

        }

        dataAdapterTourTeam.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTourTeam.setAdapter(dataAdapterTourTeam);
        spinnerTourTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        Spinner spinnerTypeTour = (Spinner) findViewById(R.id.spinnerTourTeam);
        LinearLayout title = (LinearLayout) findViewById(R.id.title);
        LinearLayout mtitle = (LinearLayout) findViewById(R.id.mtitle);
        switch (types[spinnerTypeTour.getSelectedItemPosition()]) {
            case "sp":
            case "fr":
            case "ps":
            case "gn": {
                DBTemps dbTemps = new DBTemps(this);
                DBTypeTour dbTypeTour = new DBTypeTour(this);
                dbTypeTour.open();
                dbTemps.open();
                ArrayList<Temps> tps = dbTemps.getAllTempsWithIDType(dbTypeTour.getIDWithInitial(types[spinnerTypeTour.getSelectedItemPosition()]));
                dbTemps.close();
                dbTypeTour.close();

                ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
                ListView listRank = (ListView) findViewById(R.id.listRankTeam);
                SimpleAdapter adapter;

                if (tps != null) {
                    if (tps.size() > 0) {

                        ViewGroup.LayoutParams visible = title.getLayoutParams();
                        visible.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        title.setLayoutParams(visible);

                        ViewGroup.LayoutParams invisible = mtitle.getLayoutParams();
                        invisible.height = 0;
                        mtitle.setLayoutParams(invisible);

                        DBEquipe dbEquipe = new DBEquipe(this);
                        DBCoureur dbCoureur = new DBCoureur(this);
                        dbEquipe.open();
                        dbCoureur.open();
                        for (int i = 0; i < tps.size(); ++i) {
                            listItem.add(getItem(tps.get(i).getDuree_temps(), tps.get(i).getDate_temps(), dbCoureur.getNameCoureurWithIDCoureur(tps.get(i).getId_crr_temps()), dbEquipe.getNameTeamWithIDTeam(tps.get(i).getId_equ_temps()), i + 1));
                        }
                        dbEquipe.close();
                        dbCoureur.close();

                        adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_ranking_teams,
                                new String[]{"numRank", "tempsRank", "dateRank", "playerRank", "teamRank"},
                                new int[]{R.id.numRank, R.id.tempsRank, R.id.dateRank, R.id.playerRank, R.id.teamRank});

                        listRank.setAdapter(adapter);
                    }
                } else {
                    ViewGroup.LayoutParams visible = title.getLayoutParams();
                    visible.height = 0;
                    title.setLayoutParams(visible);

                    ViewGroup.LayoutParams invisible = mtitle.getLayoutParams();
                    invisible.height = 0;
                    mtitle.setLayoutParams(invisible);

                    listItem.clear();
                    adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_ranking_teams,
                            new String[]{"numRank", "tempsRank", "dateRank", "playerRank", "teamRank"},
                            new int[]{R.id.numRank, R.id.tempsRank, R.id.dateRank, R.id.playerRank, R.id.teamRank});

                    listRank.setAdapter(adapter);
                }
                break;
            }
            case "msp":
            case "mfr":
            case "mps":
            case "mgn": {
                String type = types[spinnerTypeTour.getSelectedItemPosition()];
                type = type.substring(1);

                DBTemps dbTemps = new DBTemps(this);
                DBTypeTour dbTypeTour = new DBTypeTour(this);
                dbTypeTour.open();
                dbTemps.open();
                ArrayList<Temps> tps = dbTemps.getAVGTempsForTeamWithIDType(dbTypeTour.getIDWithInitial(type));
                dbTemps.close();
                dbTypeTour.close();

                ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
                ListView listRank = (ListView) findViewById(R.id.listRankTeam);
                SimpleAdapter adapter;
                if (tps != null) {
                    if (tps.size() > 0) {
                        ViewGroup.LayoutParams visible = mtitle.getLayoutParams();
                        visible.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        mtitle.setLayoutParams(visible);

                        ViewGroup.LayoutParams invisible = title.getLayoutParams();
                        invisible.height = 0;
                        title.setLayoutParams(invisible);

                        DBEquipe dbEquipe = new DBEquipe(this);
                        dbEquipe.open();
                        for (int i = 0; i < tps.size(); ++i) {
                            listItem.add(getItemAVG(tps.get(i).getDuree_temps(), dbEquipe.getNameTeamWithIDTeam(tps.get(i).getId_equ_temps()), i + 1));
                        }
                        dbEquipe.close();

                        adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_ranking,
                                new String[]{"numRank", "item1Rank", "item2Rank"},
                                new int[]{R.id.numRank, R.id.item1Rank, R.id.item2Rank});

                        listRank.setAdapter(adapter);
                    }
                } else {
                    ViewGroup.LayoutParams visible = title.getLayoutParams();
                    visible.height = 0;
                    title.setLayoutParams(visible);

                    ViewGroup.LayoutParams invisible = mtitle.getLayoutParams();
                    invisible.height = 0;
                    mtitle.setLayoutParams(invisible);

                    listItem.clear();
                    adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_ranking,
                            new String[]{"numRank", "item1Rank", "item2Rank"},
                            new int[]{R.id.numRank, R.id.item1Rank, R.id.item2Rank});

                    listRank.setAdapter(adapter);
                }
                break;
            }
            default:
                break;

        }
    }

    private HashMap<String, String> getItem(long duree, long date, String nameCrr, String nameTeam, int i) {
        HashMap<String, String> item = new HashMap<>();
        item.put("numRank", Integer.toString(i));
        item.put("tempsRank", getTime(duree));
        item.put("dateRank", android.text.format.DateFormat.format("HH:mm:ss dd-MM-yyyy", date).toString());
        item.put("playerRank", nameCrr);
        item.put("teamRank", nameTeam);
        return item;
    }

    private HashMap<String, String> getItemAVG(long duree, String name, int i){
        HashMap<String, String> item = new HashMap<>();
        item.put("numRank", Integer.toString(i));
        item.put("item1Rank", getTime(duree));
        item.put("item2Rank", name);
        return item;
    }

    private String getTime(long duree){
        String time;
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
        drawerToggle = new ActionBarDrawerToggle(RankingTeamActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(RankingTeamActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.player:
                        startActivity(new Intent(RankingTeamActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(RankingTeamActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(RankingTeamActivity.this, RankingPlayerActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(RankingTeamActivity.this, RankingTeamActivity.class));
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
            startActivity(new Intent(RankingTeamActivity.this, CreditsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
