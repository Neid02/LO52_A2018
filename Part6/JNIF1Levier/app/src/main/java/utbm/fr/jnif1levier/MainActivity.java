package utbm.fr.jnif1levier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView labelResult;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        labelResult  = (TextView) findViewById(R.id.label_result);

    }


    public void readOnClick(View v){
        try{
            int nb = Integer.parseInt(editText.getText().toString());

            if(nb <= 10 && nb >=0){
                labelResult.setText(jnif1Read(nb));
            } else {
                labelResult.setText("Pas de nombre entre 0 et 10.");
            }
        }catch (Exception e){
            labelResult.setText("Ceci n'est pas un nombre.");
        }
    }

    public void writeOnClick(View v){
        try{
            int nb = Integer.parseInt(editText.getText().toString());

            if(nb <= 10 && nb >=0){
                labelResult.setText(jnif1Write(nb));
            } else {
                labelResult.setText("Pas de nombre entre 0 et 10.");
            }
        }catch (Exception e){
            labelResult.setText("Ceci n'est pas un nombre.");
        }
    }

    public void translateOnClick(View v){
        labelResult.setText(jnif1Direction(((Button) v).getText().toString()));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String jnif1Read(int a);

    public native String jnif1Write(int a);

    public native String jnif1Direction(String name);
}
