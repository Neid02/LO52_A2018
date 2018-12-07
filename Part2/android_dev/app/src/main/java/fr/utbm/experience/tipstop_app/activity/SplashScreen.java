package fr.utbm.experience.tipstop_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import fr.utbm.experience.tipstop_app.R;

public class SplashScreen extends AppCompatActivity {

    private String TAG = "SplashScreen activity running.....!";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Log.i(TAG, "Bundle extras is null ");

        Log.i(TAG, "Splash screen is running...!");

        imageView = findViewById(R.id.image_logo);

        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.mytransition);

        imageView.startAnimation(myAnimation);

        Thread timer = new Thread() {

            public void run() {

                try {

                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                   startActivity(new Intent(SplashScreen.this, MainActivity.class));

                }
            }
        };


        timer.start();


    }


}
