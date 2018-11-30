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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.SpinnerTypeCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.CourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeTourDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Course;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "activity_add_course";

    private CourseDAO mDAO;
    private EditText mTxtTitre;
    private Button mBtnAdd;
    private Button mBtnCancel;
    private Stage parentStage;
    private String nowDate;
    private Spinner mSpinnerTypeCourse;
    private SpinnerTypeCourseAdapter mAdapter;
    private TypeCourse mSelectedTypeCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Ajouter une course");

        Date date = new Date();
        nowDate= new SimpleDateFormat("yyyy-MM-dd").format(date);

        Bundle parent = getIntent().getExtras();
        parentStage = new StageDAO(this).getStageById(getIntent().getLongExtra(CoursesActivity.EXTRA_SELECTED_STAGE_ID,-1));

        initViews();

        this.mDAO = new CourseDAO(this);

        //fill the spinner with companies
        List<TypeCourse> listTypeCourse = new TypeCourseDAO(this).getAllTypeCourse();
        if(listTypeCourse != null) {
            mAdapter = new SpinnerTypeCourseAdapter(this, listTypeCourse);
            mSpinnerTypeCourse.setAdapter(mAdapter);
            mSpinnerTypeCourse.setOnItemSelectedListener(this);
        }

    }

    private void initViews() {
        this.mTxtTitre = (EditText) findViewById(R.id.txt_titre);
        TextView tv = (TextView) findViewById(R.id.infoDate);
        tv.setText("Date de la course : "+nowDate);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);
        this.mBtnCancel = (Button) findViewById(R.id.btn_annuler);
        this.mSpinnerTypeCourse = (Spinner) findViewById(R.id.spinner_typeCourse);

        this.mBtnAdd.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable Titre = mTxtTitre.getText();

                if (!TextUtils.isEmpty(Titre) && parentStage != null && mSelectedTypeCourse != null) {


                    Course created = mDAO.createCourse(
                            Titre.toString(),
                            nowDate,
                            parentStage,
                            mSelectedTypeCourse
                    );

                    Log.d(TAG, "added typeTour : "+ created.getmTitre());
                    Intent intent = new Intent();
                    intent.putExtra(CoursesActivity.EXTRA_ADDED_COURSE, created);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedTypeCourse = mAdapter.getItem(position);
        Log.d(TAG, "selectedCompany : "+mSelectedTypeCourse.getmTitre());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

