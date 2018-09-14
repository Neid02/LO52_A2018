package com.rebels.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button showHelloWorldBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showHelloWorldBtn = findViewById(R.id.show_hello_world_button);
        showHelloWorldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent displayAc = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(displayAc);
            }
        });
    }
}
