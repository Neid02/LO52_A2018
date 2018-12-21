package com.example.csanchez.part5jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
        Button btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                TextView tv = (TextView) findViewById(R.id.tv);
                try {
                    tv.setText("READ " + read(Integer.parseInt(editText.getText().toString())));
                }
                catch (Exception e){

                }
            }
        }
        );

        Button btnWrite = (Button) findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            EditText editText = (EditText) findViewById(R.id.editText);
                                            TextView tv = (TextView) findViewById(R.id.tv);
                                            try{
                                                tv.setText("WRITE "+ write(Integer.parseInt(editText.getText().toString())));
                                            }
                                            catch (Exception e){
                                                // Toast.makeText(this, R.string.set_a_integer,Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }
        );

        Button btnUp = (Button) findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) findViewById(R.id.tv);
                    //Log.d("test", up().toString());
                    tv.setText(up());
                }
            }
        );
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native int read(int a);
    public native int write(int a);
    public native String up();



}

