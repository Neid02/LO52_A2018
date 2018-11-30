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
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;

public class AddStageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddStageActivity";

    private EditText mTxtTitre;
    private Button mBtnAdd;
    private Button mBtnCancel;

    private StageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Ajouter un stage");

        initViews();

        this.mDAO = new StageDAO(this);
    }

    private void initViews() {
        this.mTxtTitre = (EditText) findViewById(R.id.txt_titre);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);
        this.mBtnCancel = (Button) findViewById(R.id.btn_annuler);

        this.mBtnAdd.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable editableTitre = mTxtTitre.getText();
                if (!TextUtils.isEmpty(editableTitre)) {

                    Stage created = mDAO.createStage(
                            editableTitre.toString()
                    );

                    Log.d(TAG, "added stage : "+ created.getmTitre());
                    Intent intent = new Intent();
                    intent.putExtra(Stages.EXTRA_ADDED_STAGE, created);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(this, "Un ou plusieurs champs sont vide.", Toast.LENGTH_LONG).show();
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
