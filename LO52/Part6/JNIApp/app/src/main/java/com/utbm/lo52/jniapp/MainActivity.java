package com.utbm.lo52.jniapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText text;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
    }

    public void read(View v){
        int nb = (int)(Math.random() * (10 + 1));
        text.setText(read(nb));
    }

    public void write(View v){
        int nb = (int)(Math.random() * (10 + 1));
        text.setText(write(nb));
    }

    public void up(View v){
        text.setText(translate("up"));
    }

    public void down(View v){
        text.setText(translate("down"));
    }

    public void right(View v){
        text.setText(translate("right"));
    }

    public void left(View v){
        text.setText(translate("left"));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String read(int nombre);

    public native String write(int nombre);

    public native String translate(String nom);
}
