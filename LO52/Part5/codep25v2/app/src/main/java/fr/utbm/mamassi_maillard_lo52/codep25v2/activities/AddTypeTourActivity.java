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
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeTourDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;

public class AddTypeTourActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddTypeTourActivity";

    private TypeTourDAO mDAO;
    private EditText mTxtTitre;
    private Button mBtnAdd;
    private Button mBtnCancel;
    private TypeCourse parentTypeCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_tour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Ajouter un type de tour");

        Bundle parent = getIntent().getExtras();
        parentTypeCourse = new TypeCourseDAO(this).getTypeCourseById(getIntent().getLongExtra(ParametresTours.EXTRA_SELECTED_TYPECOURSE_ID,-1));

        initViews();

        this.mDAO = new TypeTourDAO(this);

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
                Editable typeTourTitre = mTxtTitre.getText();
                int typeTourOrdre = mDAO.getLastOrderFromTypeCourseId(parentTypeCourse.getmId());

                if (!TextUtils.isEmpty(typeTourTitre)) {

                    TypeTour created = mDAO.createTypeTour(
                            typeTourTitre.toString(),
                            typeTourOrdre,
                            parentTypeCourse
                    );

                    Log.d(TAG, "added typeTour : "+ created.getmTitre());
                    Intent intent = new Intent();
                    intent.putExtra(ParametresTours.EXTRA_ADDED_TYPETOUR, created);
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
