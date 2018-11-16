package com.example.root.codep25;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        List<Stage> stages = genererStages();

        StageAdapter adapter = new StageAdapter(MainActivity.this, stages);
        mListView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }
    private List<Stage> genererStages(){
        List<Stage> Stages = new ArrayList<Stage>();
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));
        Stages.add(new Stage("Florent", "Mon premier tweet !"));





        return Stages;
    }


}
