package fr.utbm.experience.tipstop_app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.ManifestationDao;
import fr.utbm.experience.tipstop_app.dao.RunnerDao;
import fr.utbm.experience.tipstop_app.dao.TeamDao;
import fr.utbm.experience.tipstop_app.model.Manifestation;
import fr.utbm.experience.tipstop_app.model.Runner;
import fr.utbm.experience.tipstop_app.model.Team;

public class MainActivity extends AppCompatActivity {

    private Button addManifestationButton;
    private ListView allmanifestations;

    private Dialog popupAddManifestation;
    private Dialog popupAddTeam;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Spinner spinnerTeam;
    private Spinner spinnerRunner;

    private ManifestationDao manifestationDao;
    private TeamDao teamDao;
    private RunnerDao runnerDao;

    private String TAG = "Main activity is running....!";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        popupAddManifestation = new Dialog(this);
        popupAddTeam = new Dialog(this);

        popupAddManifestation.setContentView(R.layout.layout_addmanifestation);
        popupAddTeam.setContentView(R.layout.layout_addteam);
        addManifestationButton = findViewById(R.id.button);
        allmanifestations = findViewById(R.id.list);

        spinnerTeam = popupAddTeam.findViewById(R.id.numteam);
        spinnerRunner = popupAddTeam.findViewById(R.id.mat);

        manifestationDao = new ManifestationDao(this);
        manifestationDao.open();
        teamDao = new TeamDao(this);
        teamDao.open();
        runnerDao = new RunnerDao(this);
        runnerDao.open();

        List<Manifestation> manifestations = manifestationDao.getAllManifestation();


        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<Manifestation> adapter = new ArrayAdapter<Manifestation>(this,
                android.R.layout.simple_list_item_1, manifestations);
        allmanifestations.setAdapter(adapter);

        ListView listManif = this.findViewById(R.id.list);
        listManif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intentTeam = new Intent(MainActivity.this, TeamsActivity.class);
                Log.i( "DATABASE - intent", "test");
                startActivity(intentTeam);
            }
        });


        addManifestationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText name,  lieu;
                final TextView date;
                date =  popupAddManifestation.findViewById(R.id.dateM);
                name = popupAddManifestation.findViewById(R.id.num);
                lieu = popupAddManifestation.findViewById(R.id.place);


                List<Integer> teamsIdAvailable = runnerDao.getAllTeamAvailable();
                // get and set the team containing runner
                List<Team> teamAvailable = teamDao.getTeamAvailable(teamsIdAvailable);
                //Log.i( "DATABASE-teamsA", teamAvailable.toString());
                // éléments dans une ListView
                ArrayAdapter<Team> adapterListTeam = new ArrayAdapter<Team>(MainActivity.this,
                        android.R.layout.simple_list_item_1, teamAvailable);

                ListView listTeam = popupAddTeam.findViewById(R.id.list_time);
                listTeam.setAdapter(adapterListTeam);

                popupAddManifestation.findViewById(R.id.engr).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {


                        Manifestation manifestation = new Manifestation(name.getText().toString(), date.getText().toString(), lieu.getText().toString());
                        manifestationDao.insertManifestation(manifestation);

                        List<Manifestation> manifestations = manifestationDao.getAllManifestation();
                        // éléments dans une ListView
                        ArrayAdapter<Manifestation> adapter = new ArrayAdapter<Manifestation>(MainActivity.this,
                                android.R.layout.simple_list_item_1, manifestations);
                        allmanifestations.setAdapter(adapter);


                        List<Team> teams = teamDao.getAllTeam();
                        String[] idTeams = new String[teams.size()];
                        // get id of teams
                        for (int k=0;k<teams.size();k++)
                        {
                            idTeams[k] = Integer.toString(teams.get(k).getId());
                        }
                        ArrayAdapter<String> adapterTeam = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, idTeams);
                        spinnerTeam.setAdapter(adapterTeam);


                        final List<Runner> runners = runnerDao.getAllRunner();
                        Log.i( "DATABASE - runner", runners.toString());
                        String[] matricule = new String[runners.size()];
                        // get id of teams
                        for (int k=0;k<runners.size();k++)
                        {

                            matricule[k] = runners.get(k).getR_matricule();
                        }
                        // utilisez SimpleCursorAdapter pour afficher les
                        ArrayAdapter<String> adapterRunner = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_list_item_1, matricule);

                        spinnerRunner.setAdapter(adapterRunner);
                        spinnerRunner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                TextView echelon = popupAddTeam.findViewById(R.id.echelon);
                                String matricule = spinnerRunner.getSelectedItem().toString();
                                int echelonValue = runnerDao.getEchelonByMatricule(matricule);

                                Log.i("data-runner",runners.toString());
                                Log.i("spinner- event",Integer.toString(echelonValue));
                                echelon.setText(Integer.toString(echelonValue));

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                    // your code here
                            }

                        });

                        // save a team
                        android.support.design.widget.FloatingActionButton saveTeambtn = popupAddTeam.findViewById(R.id.addRunnerB);
                         saveTeambtn.setOnClickListener(new View.OnClickListener(){
                             public void onClick(View v) {

                                 // get selected team
                                 int teamID = Integer.parseInt(spinnerTeam.getSelectedItem().toString());

                                 Spinner matriculeTextView = popupAddTeam.findViewById(R.id.mat);
                                 // get matricule of runner
                                 String matricule = matriculeTextView.getSelectedItem().toString();
                                 // insert team runner
                                 runnerDao.insertTeamRunner(teamID,matricule);

                                 /*String[] matricule = new String[runners.size()];
                                 // get id of teams
                                 for (int k=0;k<runners.size();k++)
                                 {
                                     matricule[k] = runners.get(k).getR_matricule();
                                 }

                                 ArrayAdapter<String> adapterRunner = new ArrayAdapter<String>(MainActivity.this,
                                         android.R.layout.simple_list_item_1, matricule);

                                 spinnerRunner.setAdapter(adapterRunner);*/


                                 List<Integer> teamsIdAvailable = runnerDao.getAllTeamAvailable();
                                 // get and set the team containing runner
                                 List<Team> teamAvailable = teamDao.getTeamAvailable(teamsIdAvailable);
                                 //Log.i( "DATABASE-teamsA", teamAvailable.toString());
                                 // éléments dans une ListView
                                ArrayAdapter<Team> adapterListTeam = new ArrayAdapter<Team>(MainActivity.this,
                                         android.R.layout.simple_list_item_1, teamAvailable);

                                 ListView listTeam = popupAddTeam.findViewById(R.id.list_time);
                                 listTeam.setAdapter(adapterListTeam);


                             }
                         });
                       popupAddTeam.findViewById(R.id.addTeam).setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               popupAddTeam.dismiss();
                           }
                       });
                        popupAddTeam.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupAddTeam.show();

                        popupAddManifestation.dismiss();

                    }
                });

                popupAddManifestation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupAddManifestation.show();

            }
        });
    }


    public void OpenDialog() {



    }


}
