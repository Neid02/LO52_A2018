package fr.utbm.leverdeztropfort_f1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class First extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button generer_equipes = findViewById(R.id.button_generer_equipes);
        Button voir_equipes = findViewById(R.id.button_voir_equipes);
        Button demarrer_course = findViewById(R.id.button_demarrer_course);
        Button voir_classement_indiv = findViewById(R.id.button_voir_classement_indiv);
        Button voir_classement_equipe = findViewById(R.id.button_voir_classement_equipe);

        generer_equipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferedReader bufferreader = new BufferedReader(new
                        InputStreamReader(v.getContext().getResources().openRawResource(R.raw.data), Charset.forName("UTF8")));
                String bufferLine;
                String tempText = "";
                try {
                    while ((bufferLine = bufferreader.readLine()) != null) {
                        tempText = tempText + bufferLine + "\n";
                    }

                    LinearLayout lView = new LinearLayout(v.getContext());

                    TextView myText = new TextView(v.getContext());
                    myText.setText(tempText);

                    lView.addView(myText);

                    setContentView(lView);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
