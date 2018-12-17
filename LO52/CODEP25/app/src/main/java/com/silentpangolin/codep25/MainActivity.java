package com.silentpangolin.codep25;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.support.design.widget.NavigationView;
import android.widget.Toast;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.DataBase.ORM.DBTypeTour;
import com.silentpangolin.codep25.Objects.Coureur;
import com.silentpangolin.codep25.Objects.Equipe;
import com.silentpangolin.codep25.Objects.ShakeDetector;
import com.jetradarmobile.snowfall.SnowfallView;
import com.silentpangolin.codep25.Objects.Temps;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;

    private ArrayList<Equipe> equipes;
    private boolean OnStop = true;
    private long timeWhenPaused = 0;
    private int maxTeam = 0;
    private int ordrePassage = 0;
    private ArrayList<Button> allButtons = new ArrayList<Button>();
    private int[] steps;
    private ArrayList<Temps> allTemps = new ArrayList<Temps>();
    private long[] times;
    private ArrayList<Equipe> mSelectedItems = new ArrayList<Equipe>();

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        getDataFromDataBase();

        setSpinner();

        setButton();

        initShaker();
    }


    public AlertDialog onCreateDialog() {
        mSelectedItems = new ArrayList<Equipe>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View view = layoutInflater.inflate(R.layout.dialog_choose_teams, null);

        builder.setTitle(R.string.chooseTeam);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                setTableLayoutButton(mSelectedItems);
            }
        });
        builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

    private void setTableLayoutButton(@NotNull ArrayList<Equipe> teamSelected){
        ScrollView scr = (ScrollView) findViewById(R.id.scroll);
        scr.setNestedScrollingEnabled(true);

        allButtons = new ArrayList<Button>();
        allTemps = new ArrayList<Temps>();
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

        final LinearLayout linearAllButtons = (LinearLayout) findViewById(R.id.tableLayoutButton);
        linearAllButtons.removeAllViews();

        LinearLayout linear2Button = new LinearLayout(getApplicationContext());
        linear2Button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linear2Button.setOrientation(LinearLayout.HORIZONTAL);

        times = new long[crrs.size()];

        for(int i = 0; i < crrs.size(); ++i){
            steps[i] = 0;
            times[i] = 0;
            final Button button = new Button(getApplicationContext());
            final int pos = i;
            allButtons.add(button);
            final String text = names.get(i) + " \n " + crrs.get(i).getPrenom_crr() + " " + crrs.get(i).getNom_crr() + "\n" + getString(R.string.tour) + " ";
            button.setText(text + "0");
            button.setTag(text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ++steps[pos];
                    DBTypeTour dbTypeTour = new DBTypeTour(getApplicationContext());
                    dbTypeTour.open();
                    allTemps.add(new Temps(
                            getTime(times[pos]),
                            crrs.get(pos).getId_crr(),
                            crrs.get(pos).getId_equ_crr(),
                            dbTypeTour.getIDTypeTourWithNbTours(steps[pos]),
                            System.currentTimeMillis()));
                    dbTypeTour.close();

                    times[pos] = getTime(0);

                    button.setText(text + steps[pos]);
                    if(steps[pos] == 5){
                        button.setClickable(false);
                        button.setAlpha(0.5f);
                        long temps = 0;
                        for(Temps t : allTemps){
                            if(t.getId_crr_temps() == crrs.get(pos).getId_crr()){
                                temps += t.getDuree_temps();
                            }
                        }
                        dbTypeTour.open();
                        allTemps.add(new Temps(
                                temps,
                                crrs.get(pos).getId_crr(),
                                crrs.get(pos).getId_equ_crr(),
                                dbTypeTour.getIDTypeTourWithNbTours(-1),
                                System.currentTimeMillis()));
                        dbTypeTour.close();
                    }
                    boolean flag = true;
                    for(int i = 0; i < crrs.size(); ++i)
                        if(steps[i] != 5)
                            flag = false;
                    if(flag){
                        DBTemps dbTemps = new DBTemps(getApplicationContext());
                        dbTemps.open();
                        dbTemps.insertTemps(allTemps);
                        dbTemps.close();
                        allTemps = new ArrayList<Temps>();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.saveTemps), Toast.LENGTH_LONG).show();
                        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
                        chronometer.stop();
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        timeWhenPaused = 0;
                        OnStop = !OnStop;
                        changeButtons(OnStop);
                        linearAllButtons.removeAllViews();
                    }
                }
            });
            button.setClickable(false);
            button.setAlpha(0.5f);
            button.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.f);
            button.setLayoutParams(layout);

            if(i%2 == 0){
                linear2Button.addView(button);
                if(i+1 == crrs.size()){
                    LinearLayout linear = new LinearLayout(getApplicationContext());
                    linear.setLayoutParams(layout);
                    linear2Button.addView(linear);
                    linearAllButtons.addView(linear2Button);
                }
            }
            if(i%2 == 1){
                linear2Button.addView(button);
                linearAllButtons.addView(linear2Button);
                linear2Button = new LinearLayout(getApplicationContext());
                linear2Button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                linear2Button.setOrientation(LinearLayout.HORIZONTAL);
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

    private void getDataFromDataBase(){
        DBEquipe dbEquipe = new DBEquipe(this);
        dbEquipe.open();
        equipes = dbEquipe.getAllEquipe();
        dbEquipe.close();

        DBCoureur dbCoureur = new DBCoureur(this);
        dbCoureur.open();
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
                for(int i = 0; i < allButtons.size(); ++i)
                    if(steps[i] < 5){
                        allButtons.get(i).setClickable(true);
                        allButtons.get(i).setAlpha(1.0f);
                    }
        }
    }

    private long getTime(long time){
        return SystemClock.elapsedRealtime() - ((Chronometer)findViewById(R.id.chronometer)).getBase() - time;
    }

    private HashMap<String, String> getItem(String name, String coureur) {
        HashMap<String, String> item = new HashMap<>();
        item.put("nameTeam", name);
        item.put("namesPlayer", coureur);
        return item;
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
                ListView listTeams = (ListView) alertDialog.findViewById(R.id.listTeam);

                ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
                dbCoureur.open();
                for(int i = 0; i < equipes.size(); ++i){
                    String crr = "";
                    ArrayList<Coureur> crrs = dbCoureur.getAllCoureurWithIDTeam(equipes.get(i).getId_equ());
                    for(int j = 0; j < crrs.size(); ++j){
                        crr += crrs.get(j).getNom_crr() + " ";
                        if (j != (crrs.size() - 1))
                            crr += "- ";
                    }
                    listItem.add(getItem(equipes.get(i).getName_equ(), crr));
                }
                dbCoureur.close();
                SimpleAdapter adapter = new MyPersonalAdapter(getApplicationContext(), listItem, R.layout.item_team,
                        new String[]{"nameTeam", "namesPlayer"}, new int[]{R.id.nameTeam, R.id.namesPlayer});

                listTeams.setAdapter(adapter);
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
                    for(Button b : allButtons)
                        b.setText(b.getTag() + "0");
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
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.player:
                        startActivity(new Intent(MainActivity.this, PlayerActivity.class));
                        break;
                    case R.id.team:
                        startActivity(new Intent(MainActivity.this, TeamActivity.class));
                        break;
                    case R.id.rankingPlayer:
                        startActivity(new Intent(MainActivity.this, RankingPlayerActivity.class));
                        break;
                    case R.id.rankingTeam:
                        startActivity(new Intent(MainActivity.this, RankingTeamActivity.class));
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

    public class MyPersonalAdapter extends SimpleAdapter {

        public MyPersonalAdapter(Context context, ArrayList<HashMap<String, String>> listItem, int ID,
                                 String[] from, int[] to) {
            super(context, listItem, ID, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checked);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(!checkBox.isChecked());
                    if(checkBox.isChecked()){
                        mSelectedItems.add(equipes.get(position));
                    }else if((mSelectedItems.contains(equipes.get(position))) && !(checkBox.isChecked())){
                        mSelectedItems.remove(equipes.get(position));
                    }
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        mSelectedItems.add(equipes.get(position));
                    }else if((mSelectedItems.contains(equipes.get(position))) && !(checkBox.isChecked())){
                        mSelectedItems.remove(equipes.get(position));
                    }
                }
            });

            return view;
        }
    }
}
