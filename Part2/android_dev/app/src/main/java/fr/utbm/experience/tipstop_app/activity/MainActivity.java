package fr.utbm.experience.tipstop_app.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.ManifestationDao;
import fr.utbm.experience.tipstop_app.fragment.AddManifestationFrag;
import fr.utbm.experience.tipstop_app.model.Manifestation;

public class MainActivity extends AppCompatActivity {

    private Button addManifestationButton;
    private ListView allmanifestations;
    private ManifestationDao manifestationDao;
    private Dialog popupAddManifestation;
    private Manifestation manifestation;
    private SimpleDateFormat simpleDateFormat;
    private java.sql.Date dateConverted;

    private String TAG = "Main activity is running....!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        popupAddManifestation = new Dialog(this);
        popupAddManifestation.setContentView(R.layout.layout_addmanifestation);

        addManifestationButton = findViewById(R.id.button);
        allmanifestations = findViewById(R.id.list);

        manifestationDao = new ManifestationDao(this);
        manifestationDao.open();

        List<Manifestation> manifestations = manifestationDao.getAllManifestation();

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<Manifestation> adapter = new ArrayAdapter<Manifestation>(this,
                android.R.layout.simple_list_item_1, manifestations);
        allmanifestations.setAdapter(adapter);

        addManifestationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText name, date, lieu;

                date = popupAddManifestation.findViewById(R.id.dateM);
                name = popupAddManifestation.findViewById(R.id.name);
                lieu = popupAddManifestation.findViewById(R.id.place);


                popupAddManifestation.findViewById(R.id.engr).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            Date datep = new  SimpleDateFormat().parse(date.getText().toString());
                            dateConverted =  (java.sql.Date) datep;

                                    Log.i(TAG, "Date converted : " + dateConverted.toString());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        manifestation = new Manifestation(name.getText().toString(), dateConverted, lieu.getText().toString());

                        manifestationDao.insertManifestation(manifestation);

                        popupAddManifestation.dismiss();

                    }
                });

                popupAddManifestation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupAddManifestation.show();

                //OpenDialog();
                //Intent addManifestation = new Intent(MainActivity.this, addManifestation.class);
                //startActivity(addManifestation);
            }
        });
    }


    public void OpenDialog() {



    }


}
