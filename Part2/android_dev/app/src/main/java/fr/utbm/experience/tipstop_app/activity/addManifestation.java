package fr.utbm.experience.tipstop_app.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Date;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.ManifestationDao;
import fr.utbm.experience.tipstop_app.model.Manifestation;

public class addManifestation extends AppCompatActivity {

    private ManifestationDao manifestationDao;
    private Button saveManifestion;
    private Dialog popupAddManifestation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        popupAddManifestation.setContentView(R.layout.activity_add_manifestation);


        saveManifestion = (Button)findViewById(R.id.button) ;
        manifestationDao = new ManifestationDao(this);
        manifestationDao.open();

    }


    public void onClick(View view) {

        Date myDate = new Date(12102018);
        Manifestation manifestation = new Manifestation("TipStop",myDate,"Belfort");
        manifestationDao.insertManifestation(manifestation);

    }


}
