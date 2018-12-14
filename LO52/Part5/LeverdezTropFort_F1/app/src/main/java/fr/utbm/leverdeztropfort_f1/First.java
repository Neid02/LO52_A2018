package fr.utbm.leverdeztropfort_f1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class First extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button voir_equipes = findViewById(R.id.button_voir_equipes);
        Button demarrer_course = findViewById(R.id.button_demarrer_course);
        Button voir_classement_indiv = findViewById(R.id.button_voir_classement_indiv);
        Button voir_classement_equipe = findViewById(R.id.button_voir_classement_equipe);

        voir_equipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Afficher_equipes.class);
                v.getContext().startActivity(intent);
            }
        });

        demarrer_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Demarrer_course.class);
                v.getContext().startActivity(intent);
            }
        });

        voir_classement_indiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Afficher_classment_indiv.class);
                v.getContext().startActivity(intent);
            }
        });

        voir_classement_equipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Afficher_classment_equipe.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
