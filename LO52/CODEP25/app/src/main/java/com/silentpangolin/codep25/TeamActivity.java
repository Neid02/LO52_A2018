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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Equipe;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private ArrayList<Equipe> equipes;
    private ListView listEqu;
    private ArrayList<HashMap<String, String>> listItem;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        initInstances();

        DBEquipe dbEquipe = new DBEquipe(this);
        dbEquipe.open();
        equipes = dbEquipe.getAllEquipe();
        dbEquipe.close();

        listEqu = (ListView) findViewById(R.id.listEqu);

        listItem = new ArrayList<>();

        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
        if(equipes.size() > 0)
            for(int i = 0; i < equipes.size(); ++i){
                listItem.add(getItem(equipes.get(i), dbCoureur.getAllCoureurWithIDTeam(equipes.get(i).getId_equ()), i + 1));
            }
        dbCoureur.close();

        adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_equipe,
                new String[]{"numEquipe", "nomEquipe", "sumEchelon",
                        "ordreCoureur1", "nom1", "prenom1", "echelon1",
                        "ordreCoureur2", "nom2", "prenom2", "echelon2",
                        "ordreCoureur3", "nom3", "prenom3", "echelon3"},
                new int[]{R.id.numEquipe, R.id.nomEquipe, R.id.sumEchelon,
                        R.id.ordreCoureur1, R.id.nom1, R.id.prenom1, R.id.echelon1,
                        R.id.ordreCoureur2, R.id.nom2, R.id.prenom2, R.id.echelon2,
                        R.id.ordreCoureur3, R.id.nom3, R.id.prenom3, R.id.echelon3});

         listEqu.setAdapter(adapter);
    }

    public HashMap<String, String> getItem(Equipe e, ArrayList<Coureur> crrs, int position) {
         HashMap<String, String> item = new HashMap<>();
         item.put("numEquipe", Integer.toString(position));
         item.put("nomEquipe", e.getName_equ());
         int total = 0;
         for(int i = 0; i < crrs.size(); ++i){
             item.put("ordreCoureur" + (i + 1), Integer.toString(crrs.get(i).getOrdrepassage_crr()));
             item.put("nom" + (i + 1), crrs.get(i).getNom_crr());
             item.put("prenom" + (i + 1), crrs.get(i).getPrenom_crr());
             item.put("echelon" + (i + 1), Integer.toString(crrs.get(i).getEchelon_crr()));
             total += crrs.get(i).getEchelon_crr();
         }
         if (crrs.size() == 2){
             item.put("ordreCoureur3", "");
             item.put("nom3", "");
             item.put("prenom3", "");
             item.put("echelon3", "");
         }
         item.put("sumEchelon", Integer.toString(total));
         return item;
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(TeamActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(TeamActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.player:
                        startActivity(new Intent(TeamActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(TeamActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(TeamActivity.this, RankingPlayerActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(TeamActivity.this, RankingTeamActivity.class));
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
            startActivity(new Intent(TeamActivity.this, CreditsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
