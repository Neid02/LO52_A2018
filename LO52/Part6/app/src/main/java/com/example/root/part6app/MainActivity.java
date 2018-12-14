package com.example.root.part6app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txt;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        txt = (TextView) findViewById(R.id.txt);
        Button button_down = (Button) findViewById(R.id.button);
        Button button_up = (Button) findViewById(R.id.button3);
        Button button_left = (Button) findViewById(R.id.button2);
        Button button_right = (Button) findViewById(R.id.button4);
        Button button_read = (Button) findViewById(R.id.button6);
        Button button_write = (Button) findViewById(R.id.button5);

        button_read.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txt.setText("READ : " + textAAfficheReadFromJNI(Math.random() * (10 - 0)));
            }
        });

        button_write.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txt.setText("WRITE : " + textAAfficheWriteFromJNI(Math.random() * (10 - 0)));
            }
        });

        button_right.setOnClickListener(this);
        button_left.setOnClickListener(this);
        button_down.setOnClickListener(this);
        button_up.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                txt.setText(textDeLaDirectionFromJNI("DOWN"));
                break;

            case R.id.button3:
                txt.setText(textDeLaDirectionFromJNI("UP"));
                break;
            case R.id.button2:
                txt.setText(textDeLaDirectionFromJNI("LEFT"));
                break;
            case R.id.button4:
                txt.setText(textDeLaDirectionFromJNI("RIGHT"));
                break;

            default:
                break;
        }



    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String textAAfficheReadFromJNI(double nombre);
    public native String textAAfficheWriteFromJNI(double nombre);
    public native String textDeLaDirectionFromJNI(String nom);
}

