package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListTypeCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListTypeTourAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeTourDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;

public class ParametresTours extends AppCompatActivity {

    public static final String TAG = "ParametresTours";

    public static final int REQUEST_CODE_ADD_TYPETOUR = 40;
    public static final String EXTRA_ADDED_TYPETOUR = "extra_key_added_type_tour";
    public static final String EXTRA_SELECTED_TYPECOURSE_ID = "extra_key_selected_TypeCourse_id";

    private ListView mListView;
    private TextView mTxtEmptyListTypeTour;

    private ListTypeTourAdapter mAdapter;
    private List<TypeTour> mListItems;
    private TypeTourDAO mDao;
    Context mContext = this;

    private long mTypeCourseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_tours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent  = getIntent();
        if(intent != null) {
            this.mTypeCourseId = intent.getLongExtra(EXTRA_SELECTED_TYPECOURSE_ID, -1);
        }

        //Bouton ajouter
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddTypeTourActivity.class);
                Bundle b = new Bundle();
                b.putLong(EXTRA_SELECTED_TYPECOURSE_ID, mTypeCourseId);
                intent.putExtras(b);
                startActivityForResult(intent, REQUEST_CODE_ADD_TYPETOUR);
            }
        });
        //=====================================================================================

        // initialize views
        initViews();

        // fill the listView
        mDao = new TypeTourDAO(this);
        if(this.mTypeCourseId != -1){
            mListItems = mDao.getTypeTourOfTypeCourse(this.mTypeCourseId);
            setTitle("Course : " + new TypeCourseDAO(mContext).getTypeCourseById(this.mTypeCourseId).getmTitre());
        }
        if(mListItems != null && !mListItems.isEmpty()) {
            mAdapter = new ListTypeTourAdapter(this, mListItems);
            mListView.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListTypeTour.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        this.mListView = (ListView) findViewById(R.id.list_TypeCourse);
        this.mTxtEmptyListTypeTour = (TextView) findViewById(R.id.txt_empty_list_TypeTour);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_TYPETOUR) {
            if(resultCode == RESULT_OK) {

                if(data != null) {
                    TypeTour created = (TypeTour) data.getSerializableExtra(EXTRA_ADDED_TYPETOUR);
                    if(created != null) {
                        if(mListItems == null)
                            mListItems = new ArrayList<TypeTour>();
                        mListItems.add(created);

                        if(mAdapter == null) {
                            if(mListView.getVisibility() != View.VISIBLE) {
                                mListView.setVisibility(View.VISIBLE);
                                mTxtEmptyListTypeTour.setVisibility(View.GONE);
                            }

                            mAdapter = new ListTypeTourAdapter(this, mListItems);
                            mListView.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListItems);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDao.close();
    }

}
