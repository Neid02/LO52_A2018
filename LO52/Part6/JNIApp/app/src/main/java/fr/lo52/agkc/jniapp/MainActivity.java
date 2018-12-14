package fr.lo52.agkc.jniapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText def;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        def = findViewById(R.id.def);
    }


    public void read(View v){
        int nb = (int)(Math.random() * ((10)));
        // Example of a call to a native method
        def.setText(lecture(nb));
    }

    public void write(View v){
        int nb = (int)(Math.random() * ((10)));
        // Example of a call to a native method
        def.setText(ecriture(nb));
    }

    public void up(View v){
        // Example of a call to a native method

        def.setText(translate("up"));
    }

    public void down(View v){
        // Example of a call to a native method
        def.setText(translate("down"));
    }

    public void right(View v){
        // Example of a call to a native method
       def.setText(translate("right"));
    }

    public void left(View v){
        // Example of a call to a native method
        def.setText(translate("left"));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

    public native String lecture(int nombre);

    public native String ecriture(int nombre);

    public native String translate(String nom);


}
