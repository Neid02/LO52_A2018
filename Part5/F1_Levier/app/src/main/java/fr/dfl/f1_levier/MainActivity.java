package fr.dfl.f1_levier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button participantsButton = findViewById(R.id.button_participants);
        participantsButton.setOnClickListener(this);

        Button equipesButton = findViewById(R.id.button_equipes);
        equipesButton.setOnClickListener(this);

        Button resultatsButton = findViewById(R.id.button_resultats);
        resultatsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_participants :
                //intent = new Intent(this, ParticipantsActivity.class); // TODO : Uncomment after creating the associated activity
                break;
            case R.id.button_equipes :
                //intent = new Intent(this, EquipesActivity.class); // TODO : Uncomment after creating the associated activity
                break;
            case R.id.button_resultats :
                //intent = new Intent(this, ResultatsActivity.class); // TODO : Uncomment after creating the associated activity
                break;
        }
        startActivity(intent);
    }
}
