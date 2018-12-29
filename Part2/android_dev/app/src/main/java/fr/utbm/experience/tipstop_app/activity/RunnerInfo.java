package fr.utbm.experience.tipstop_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.RunnerDao;
import fr.utbm.experience.tipstop_app.dao.TimeRunnerDao;
import fr.utbm.experience.tipstop_app.model.Runner;
import fr.utbm.experience.tipstop_app.model.TimeRunner;

public class RunnerInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_info);

        String matRunner= getIntent().getStringExtra("idRunner");

        RunnerDao runnerDao = new RunnerDao(this);
        runnerDao.open();

        final Runner runner = runnerDao.getRunnerByMatricule(matRunner);
        Log.i("runnerActivity", runner.toString());
        EditText matricule = this.findViewById(R.id.matEdit);
        EditText name = this.findViewById(R.id.nomEdit);
        EditText echelon =this.findViewById(R.id.pts);

        matricule.setText(runner.getR_matricule());
        name.setText(runner.getR_name());
        //echelon.setText(Integer.toString(runner.getR_echelon()));

        TimeRunnerDao timeRunnerDao = new TimeRunnerDao(this);
        timeRunnerDao.open();
        TimeRunner timeRunner = timeRunnerDao.getTimeRunner(runner.getR_matricule());

        /* int t1_Sprint;
         int t1_Fract;
         int t1_PitStop;
         int t2_Sprint;
         int t2_Fract;
         float Moy;
         int Passage;
         String r_RunnerMat;*/

        Hashtable ht = new Hashtable();
        ht.put("t1_Sprint",timeRunner.getT1_Sprint());
        ht.put("t1_Fract", timeRunner.getT1_Fract());
        ht.put("t1_PitStop", timeRunner.getT1_PitStop());

        ArrayList<List<String>> arr = new ArrayList<List<String>>(ht.values());

        final ArrayAdapter<List<String>> adapterTimeRunner = new ArrayAdapter<List<String>>(this, android.R.layout.simple_list_item_1, arr);
         ListView listRunner = this.findViewById(R.id.list_time);
        listRunner.setAdapter(adapterTimeRunner);
        listRunner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentTeam = new Intent(RunnerInfo.this,TimeActivity.class);

                intentTeam.putExtra("idTime", runner.getR_matricule());
                startActivity(intentTeam);
            }
        });
    }
}
