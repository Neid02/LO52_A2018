package adury_csanchez.utbm.f1levier.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import adury_csanchez.utbm.f1levier.R;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.buttonGoToRace);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.buttonGoToRace).setEnabled(false);
                startActivity(new Intent(MainActivity.this, RaceActivity.class));

            }
        });
    }
}

