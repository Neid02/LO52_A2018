package fr.utbm.experience.tipstop_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.RunnerDao;
import fr.utbm.experience.tipstop_app.model.Runner;
import fr.utbm.experience.tipstop_app.model.Team;

public class ListRunnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_runner);
        String sessionId= getIntent().getStringExtra("id");
        Log.i("changeData", sessionId);
        RunnerDao runnerDao = new RunnerDao(this);
        runnerDao.open();

        List<Runner> runnerList = runnerDao.getAvailableRunner(Integer.parseInt(sessionId));
        Log.i("sessionList", runnerList.toString());
        final ArrayAdapter<Runner> adapterListRunner = new ArrayAdapter<Runner>(this,
                android.R.layout.simple_list_item_1, runnerList);

        final ListView listRunner = this.findViewById(R.id.list_runner);
        listRunner.setAdapter(adapterListRunner);
        listRunner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentTeam = new Intent(ListRunnerActivity.this, RunnerInfo.class);

                intentTeam.putExtra("idRunner", adapterListRunner.getItem(position).getR_matricule());
                startActivity(intentTeam);
            }
        });


        //Log.i("changeData", sessionId);
    }
}
