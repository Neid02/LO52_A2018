package fr.utbm.experience.tipstop_app.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.utbm.experience.tipstop_app.R;

public class AddManifestationFrag extends DialogFragment {

    private EditText name;
    private EditText dateM;
    private EditText place;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_addmanifestation, null);

        builder.setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Log.i("DialogBox", "Validation");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("DialogBox", "Cancel");
                    }
                });

        name = view.findViewById(R.id.num);
        dateM = view.findViewById(R.id.dateM);
        place = view.findViewById(R.id.place);

        // Create the AlertDialog object and return it
        return builder.create();
    }


    public void onClick(View view) {

      /*  String myDate = null;
        ManifestationDao manifestationDao = new ManifestationDao(MainActivity.this);
        Manifestation manifestation = new Manifestation("TipStop",myDate,"Belfort");
        manifestationDao.insertManifestation(manifestation);*/


    }
}
