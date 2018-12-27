package com.silentpangolin.codep25;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.DataBase.ORM.DBTypeTour;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Temps;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private ListView listCrr;
    private ArrayList<HashMap<String, String>> listItem;
    private SimpleAdapter adapter;
    private ArrayList<Integer> IDcrr;
    private ArrayList<Coureur> allCrrs;
    private boolean modify = false;
    private int easteregg = 16;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initInstances();

        createArrayList();

        setButton();
    }

    private void setButton(){
        ImageButton add = (ImageButton) findViewById(R.id.addCrr);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(PlayerActivity.this);
                final View myView = layoutInflater.inflate(R.layout.dialog_modify_crr, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
                builder.setView(myView);
                builder.setTitle(getResources().getString(R.string.addingtitle));
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NumberPicker echelon = (NumberPicker) myView.findViewById(R.id.modifyEditEchelon);
                        EditText nom = (EditText) myView.findViewById(R.id.modifyEditNom);
                        EditText prenom = (EditText) myView.findViewById(R.id.modifyEditPrenom);
                        if(!(nom.getText().toString().equals("")) && !(prenom.getText().toString().equals(""))
                                && !(nom.getText().toString().replace(" ", "").equals("")) && !(prenom.getText().toString().replace(" ", "").equals(""))){
                            modify = true;
                            Coureur c = new Coureur();
                            c.setNom_crr(nom.getText().toString());
                            c.setPrenom_crr(prenom.getText().toString());
                            c.setEchelon_crr(echelon.getValue());

                            allCrrs.add(c);

                            listItem.add(getItem(c, allCrrs.indexOf(c) + 1, 0));
                            adapter.notifyDataSetChanged();
                            DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
                            dbCoureur.open();
                            dbCoureur.insertCoureur(c);
                            dbCoureur.close();
                        }
                    }
                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setIcon(getResources().getDrawable(R.drawable.modify));
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                try{
                    NumberPicker echelon = (NumberPicker) alertDialog.findViewById(R.id.modifyEditEchelon);
                    echelon.setMaxValue(100);
                    echelon.setMinValue(0);
                    echelon.setWrapSelectorWheel(false);
                }catch(Exception e){
                    e.printStackTrace();
                }
                (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)).setTextColor(getResources().getColor(R.color.grey_black));
                (alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)).setTextColor(getResources().getColor(R.color.grey_black));
            }
        });

    }

    @Override
    protected void onPause(){
        if(modify)
            startActivity(new Intent(PlayerActivity.this, LoadingActivity.class));
        super.onPause();
    }

    private void createArrayList(){
        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
        allCrrs = dbCoureur.getAllCoureur();
        dbCoureur.close();

        listCrr = (ListView) findViewById(R.id.listCrr);

        listItem = new ArrayList<HashMap<String, String>>();
        IDcrr = new ArrayList<Integer>();

        DBTypeTour dbTypeTour = new DBTypeTour(this);
        dbTypeTour.open();
        int type = dbTypeTour.getIDWithInitial("gn");
        dbTypeTour.close();

        DBTemps dbTemps = new DBTemps(this);
        dbTemps.open();
        if (allCrrs.size() > 0) {
            for (int i = 0; i < allCrrs.size(); ++i) {
                Temps tps = dbTemps.getAVGTempsForPlayerWithIDType(type, allCrrs.get(i).getId_crr());
                if (tps != null){
                    listItem.add(getItem(allCrrs.get(i), i + 1, tps.getDuree_temps()));
                }else{
                    listItem.add(getItem(allCrrs.get(i), i + 1, 0));
                }
                IDcrr.add(allCrrs.get(i).getId_crr());
            }
        }
        dbTemps.close();

        adapter = new MyPersonalAdapter(this.getBaseContext(), listItem, R.layout.list_coureur,
                new String[]{"num", "nom", "prenom", "echelon", "moy"}, new int[]{R.id.numCrr, R.id.nomCrr, R.id.prenomCrr, R.id.echelonCrr, R.id.tempsCrr});

        listCrr.setAdapter(adapter);
    }

    private HashMap<String, String> getItem(Coureur c, int i, long duree) {
        HashMap<String, String> item = new HashMap<>();
        item.put("num", Integer.toString(i));
        item.put("nom", c.getNom_crr());
        item.put("prenom", c.getPrenom_crr());
        item.put("echelon", Integer.toString(c.getEchelon_crr()));
        item.put("moy", getTime(duree));
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
        drawerToggle = new ActionBarDrawerToggle(PlayerActivity.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.player:
                        startActivity(new Intent(PlayerActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(PlayerActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(PlayerActivity.this, RankingPlayerActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(PlayerActivity.this, RankingTeamActivity.class));
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

    public class MyPersonalAdapter extends SimpleAdapter {

        public MyPersonalAdapter(Context context, ArrayList<HashMap<String, String>> listItem, int ID,
                                 String[] from, int[] to) {
            super(context, listItem, ID, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            /** EASTER EGG */
            if(position == easteregg - 1){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ++count;
                        if(count == easteregg)
                            Toast.makeText(getApplicationContext(), "PROMO 16 !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            ImageButton deleteCrr = (ImageButton) view.findViewById(R.id.deleteCrr);
            deleteCrr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDeleteDialog(position);
                }
            });

            ImageButton modifyCrr = (ImageButton) view.findViewById(R.id.modifyCrr);
            modifyCrr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setModifyDialog(position);
                }
            });
            return view;
        }

        private void setModifyDialog(final int position){
            LayoutInflater layoutInflater = LayoutInflater.from(PlayerActivity.this);
            final View view = layoutInflater.inflate(R.layout.dialog_modify_crr, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
            builder.setView(view);
            builder.setTitle(getResources().getString(R.string.modifytitle));
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    NumberPicker echelon = (NumberPicker) view.findViewById(R.id.modifyEditEchelon);
                    EditText nom = (EditText) view.findViewById(R.id.modifyEditNom);
                    EditText prenom = (EditText) view.findViewById(R.id.modifyEditPrenom);
                    if(!(nom.getText().toString().equals("")) && !(prenom.getText().toString().equals(""))
                            && !(nom.getText().toString().replace(" ", "").equals("")) && !(prenom.getText().toString().replace(" ", "").equals(""))){
                        if(allCrrs.get(position).getEchelon_crr() != echelon.getValue())
                            modify = true;
                        allCrrs.get(position).setEchelon_crr(echelon.getValue());
                        allCrrs.get(position).setNom_crr(nom.getText().toString());
                        allCrrs.get(position).setPrenom_crr(prenom.getText().toString());

                        listItem.set(position, getItem(allCrrs.get(position), position + 1));
                        adapter.notifyDataSetChanged();
                        DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
                        dbCoureur.open();
                        dbCoureur.updateCoureur(allCrrs.get(position));
                        dbCoureur.close();
                    }
                }
            });
            builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setIcon(getResources().getDrawable(R.drawable.modify));
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            try{
                NumberPicker echelon = (NumberPicker) alertDialog.findViewById(R.id.modifyEditEchelon);
                echelon.setMaxValue(100);
                echelon.setMinValue(0);
                echelon.setValue(allCrrs.get(position).getEchelon_crr());
                echelon.setWrapSelectorWheel(false);

                EditText nom = (EditText) alertDialog.findViewById(R.id.modifyEditNom);
                nom.setText(allCrrs.get(position).getNom_crr());

                EditText prenom = (EditText) alertDialog.findViewById(R.id.modifyEditPrenom);
                prenom.setText(allCrrs.get(position).getPrenom_crr());
            }catch(Exception e){
                e.printStackTrace();
            }
            (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)).setTextColor(getResources().getColor(R.color.grey_black));
            (alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)).setTextColor(getResources().getColor(R.color.grey_black));
        }

        private void setDeleteDialog(final int position){
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this);
            builder.setTitle(getResources().getString(R.string.deleteCrr));
            builder.setMessage(allCrrs.get(position).getPrenom_crr() + " " + allCrrs.get(position).getNom_crr());
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    DBTemps dbTemps = new DBTemps(getApplicationContext());
                    dbTemps.open();
                    dbTemps.removeTempsWithIDCoureur(allCrrs.get(position).getId_crr());
                    dbTemps.close();
                    DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
                    dbCoureur.open();
                    dbCoureur.deleteCoureur(allCrrs.get(position).getId_crr());
                    dbCoureur.close();
                    listItem.remove(position);
                    allCrrs.remove(position);
                    adapter.notifyDataSetChanged();
                    modify = true;
                }
            });
            builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setIcon(getResources().getDrawable(R.drawable.delete));

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)).setTextColor(getResources().getColor(R.color.grey_black));
            (alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)).setTextColor(getResources().getColor(R.color.grey_black));
        }

        private HashMap<String, String> getItem(Coureur c, int i) {
            HashMap<String, String> item = new HashMap<>();
            item.put("num", Integer.toString(i));
            item.put("nom", c.getNom_crr());
            item.put("prenom", c.getPrenom_crr());
            item.put("echelon", Integer.toString(c.getEchelon_crr()));
            return item;
        }
    }
}
