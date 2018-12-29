package fr.utbm.experience.tipstop_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

import fr.utbm.experience.tipstop_app.R;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }

    public void startChronometer(View view) {
        ((Chronometer) findViewById(R.id.chronometer1)).start();
    }

    public void stopChronometer(View view) {
        ((Chronometer) findViewById(R.id.chronometer1)).stop();
    }
}
