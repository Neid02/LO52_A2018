package c.bollytachi.easyswitch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShowHelloWorld extends AppCompatActivity {

    private ImageView btn_back_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_back_main = findViewById(R.id.btn_back_main);
        btn_back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowHelloWorld.this, MainActivity.class));
                finish();

            }
        });

    }
}
