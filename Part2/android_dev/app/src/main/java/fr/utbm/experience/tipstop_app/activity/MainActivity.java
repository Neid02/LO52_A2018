package fr.utbm.experience.tipstop_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import fr.utbm.experience.tipstop_app.R;
import fr.utbm.experience.tipstop_app.dao.ManifestationDao;
import fr.utbm.experience.tipstop_app.fragment.AddManifestationFrag;
import fr.utbm.experience.tipstop_app.model.Manifestation;

public class MainActivity extends AppCompatActivity {

    private Button addManifestationButton;
    private ListView allmanifestations;
    private ManifestationDao manifestationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addManifestationButton = (Button)findViewById(R.id.button);
        allmanifestations = (ListView)findViewById(R.id.list);

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

                OpenDialog();
                //Intent addManifestation = new Intent(MainActivity.this, addManifestation.class);
                //startActivity(addManifestation);
            }
        });
    }
    public void OpenDialog()
    {
       AddManifestationFrag addManifestationFrag = new AddManifestationFrag();
       addManifestationFrag.show(getFragmentManager(),"Add manifestation");
    }


}
