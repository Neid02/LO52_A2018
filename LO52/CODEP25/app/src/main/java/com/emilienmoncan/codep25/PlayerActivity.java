package com.emilienmoncan.codep25;

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

import com.emilienmoncan.codep25.DataBase.ORM.DBCoureur;
import com.emilienmoncan.codep25.Objects.Coureur;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private ListView listCrr;
    private ArrayList<HashMap<String, String>> listItem;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        setTitle(R.string.listcrr);

        initInstances();

        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
        ArrayList<Coureur> crrs = dbCoureur.getAllCoureur();
        dbCoureur.close();

        listCrr = (ListView) findViewById(R.id.listCrr);

        listItem = new ArrayList<>();

        if (crrs.size() > 0)
            for (int i = 0; i < crrs.size(); ++i)
                listItem.add(getItem(crrs.get(i), i + 1));

        adapter = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_coureur,
                new String[]{"num", "nom", "prenom", "echelon"}, new int[]{R.id.numCrr, R.id.nomCrr, R.id.prenomCrr, R.id.echelonCrr});

        listCrr.setAdapter(adapter);
    }

    public HashMap<String, String> getItem(Coureur c, int i) {
        HashMap<String, String> item = new HashMap<>();
        item.put("num", Integer.toString(i));
        item.put("nom", c.getNom_crr());
        item.put("prenom", c.getPrenom_crr());
        item.put("echelon", Integer.toString(c.getEchelon_crr()));
        return item;
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(PlayerActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        startActivity(new Intent(PlayerActivity.this, MainActivity.class));
                        break;
                    case R.id.player:
                        startActivity(new Intent(PlayerActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(PlayerActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(PlayerActivity.this, MainActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(PlayerActivity.this, MainActivity.class));
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
            startActivity(new Intent(PlayerActivity.this, CreditsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
