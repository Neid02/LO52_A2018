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

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.RunnerDao;
import fr.utbm.experience.tipstop_app.dao.TimeRunnerDao;
import fr.utbm.experience.tipstop_app.model.Runner;
import fr.utbm.experience.tipstop_app.model.TimeRunner;

public class RunnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);

        String matRunner= getIntent().getStringExtra("idRunner");

        RunnerDao runnerDao = new RunnerDao(this);
        runnerDao.open();

        Runner runner = runnerDao.getRunnerByMatricule(matRunner);
        Log.i("runnerActivity", runner.toString());
        EditText matricule = this.findViewById(R.id.matEdit);
        EditText name = this.findViewById(R.id.nomEdit);
        EditText echelon =this.findViewById(R.id.pts);

        matricule.setText(runner.getR_matricule());
        name.setText(runner.getR_name());
        echelon.setText(runner.getR_echelon());

        TimeRunnerDao timeRunnerDao = new TimeRunnerDao(this);
        timeRunnerDao.open();
        TimeRunner timeRunner = timeRunnerDao.getTimeRunner(runner.getR_matricule());
        Log.i("Time-runner", timeRunner.toString());
        /*final ArrayAdapter<Runner> adapterListRunner = new ArrayAdapter<Runner>(this,
                android.R.layout.simple_list_item_1, runnerList);

        final ListView listRunner = this.findViewById(R.id.list_runner);
        listRunner.setAdapter(adapterListRunner);
        listRunner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentTeam = new Intent(ListRunnerActivity.this, RunnerInfo.class);

                intentTeam.putExtra("idRunner", adapterListRunner.getItem(position).getR_matricule());
                startActivity(intentTeam);
            }
        });*/

    }
}
