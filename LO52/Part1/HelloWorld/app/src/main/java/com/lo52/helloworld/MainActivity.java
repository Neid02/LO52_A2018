package com.lo52.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_hello = findViewById(R.id.btn_id);
        button_hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helloac = new Intent(MainActivity.this, HelloWordActivity.class);
                startActivity(helloac);
            }
        });
    }
}
