package fr.utbm.agueye.hello_word;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class Partie1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie1);


        Button button = findViewById(R.id.button);

		button.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Partie1.this,
                    second_act.class);
            startActivity(intent); // startActivity allow you to move
        }

    });
    }
}
