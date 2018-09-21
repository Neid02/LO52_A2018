package fr.utbm.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void open_newact(View view)
    {
        Intent intent = new Intent(MainActivity.this, HelloWorldsecondActivity.class);
        startActivity(intent);
    }
}
