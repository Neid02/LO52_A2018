package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.ParticipantDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;

public class AddParticipantActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddParticipantActivity";

    private ParticipantDAO mDAO;
    private EditText mTxtNom;
    private EditText mTxtPrenom;
    private EditText mTxtEchelon;
    private Button mBtnAdd;
    private Button mBtnCancel;
    private Stage parentStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Ajouter un participant");

        Bundle parent = getIntent().getExtras();
        parentStage = new StageDAO(this).getStageById(getIntent().getLongExtra(CoursesActivity.EXTRA_SELECTED_STAGE_ID,-1));

        initViews();

        this.mDAO = new ParticipantDAO(this);

    }

    private void initViews() {
        this.mTxtNom = (EditText) findViewById(R.id.txt_nom);
        this.mTxtPrenom = (EditText) findViewById(R.id.txt_prenom);
        this.mTxtEchelon = (EditText) findViewById(R.id.txt_echelon);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);
        this.mBtnCancel = (Button) findViewById(R.id.btn_annuler);

        this.mBtnAdd.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable nom = mTxtNom.getText();
                Editable prenom = mTxtPrenom.getText();
                Editable echelon = mTxtEchelon.getText();

                if (!TextUtils.isEmpty(nom) && parentStage != null && !TextUtils.isEmpty(prenom) && !TextUtils.isEmpty(echelon)) {


                    Participant created = mDAO.createParticipant(
                            nom.toString(),
                            prenom.toString(),
                            Integer.parseInt(echelon.toString()),
                            parentStage
                    );

                    Log.d(TAG, "added Participant : "+ created.getmNom() + " " +created.getmPrenom());
                    Intent intent = new Intent();
                    intent.putExtra(ParticipantsActivity.EXTRA_ADDED_PARTICIPANT, created);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast.makeText(this, "Un ou plusieurs champs sont vide", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_annuler:
                setResult(RESULT_CANCELED);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDAO.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }
        return true;
    }
}


