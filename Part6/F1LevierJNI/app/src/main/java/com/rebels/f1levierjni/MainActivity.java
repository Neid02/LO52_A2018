package com.rebels.f1levierjni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'f1levierjni-main' library on application startup.
    static {
        System.loadLibrary("f1levierjni-main");
    }

    private final int MIN_PICKED_INT = 0;
    private final int MAX_PICKED_INT = 10;
    private final Random random = new Random();

    EditText defEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get wiews elements by id
        //      EditText
        defEditText = findViewById(R.id.defEditText);
    }

    private native String getReadString(int a);
    private native String getWriteString(int a);
    private native String getGermanTranslatedText(String entry);


    /**
     * Picks a random integer between 'min' and 'max'
     *
     * @param min int
     * @param max int
     * @return int
     */
    private int pickInteger(int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Sets the defEditText's content with the read message
     *
     * @param view View
     */
    public void doRead(View view) {
        defEditText.setText(getReadString(pickInteger(MIN_PICKED_INT, MAX_PICKED_INT)));
    }

    /**
     * Sets the defEditText's content with the write message
     *
     * @param view View
     */
    public void doWrite(View view) {
        defEditText.setText(getWriteString(pickInteger(MIN_PICKED_INT, MAX_PICKED_INT)));
    }

    /**
     * Sets the defEditText's content with the clicked button's text german translation
     *
     * @param view View
     */
    public void doDirection(View view) {
        Button clickedButton = findViewById(view.getId());
        String newText = getGermanTranslatedText(clickedButton.getText().toString().toLowerCase());
        defEditText.setText(newText.toUpperCase());
    }
}
