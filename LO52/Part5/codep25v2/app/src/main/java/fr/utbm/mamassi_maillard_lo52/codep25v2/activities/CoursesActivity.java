package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListTypeTourAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.CourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeTourDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Course;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;

public class CoursesActivity extends AppCompatActivity {

    public static final String TAG = "CoursesActivity";

    public static final int REQUEST_CODE_ADD_COURSE = 40;
    public static final int REQUEST_CODE_ACTIVITY_PARTICIPANTS = 30;
    public static final String EXTRA_ADDED_COURSE = "extra_key_added_course";
    public static final String EXTRA_SELECTED_STAGE_ID = "extra_key_selected_stage_id";

    private ListView mListView;
    private TextView mTxtEmptyListCourse;

    private ListCourseAdapter mAdapter;
    private List<Course> mListItems;
    private CourseDAO mDao;
    Context mContext = this;

    private long mStageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent  = getIntent();
        if(intent != null) {
            this.mStageId = intent.getLongExtra(EXTRA_SELECTED_STAGE_ID, -1);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddCourseActivity.class);
                Bundle b = new Bundle();
                b.putLong(EXTRA_SELECTED_STAGE_ID, mStageId);
                intent.putExtras(b);
                startActivityForResult(intent, REQUEST_CODE_ADD_COURSE);
            }
        });

        FloatingActionButton fabParticipant = (FloatingActionButton) findViewById(R.id.fabParticipant);
        fabParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ParticipantsActivity.class);
                Bundle b = new Bundle();
                b.putLong(EXTRA_SELECTED_STAGE_ID, mStageId);
                intent.putExtras(b);
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_PARTICIPANTS);
            }
        });

        // initialize views
        initViews();

        // fill the listView
        mDao = new CourseDAO(this);
        if(this.mStageId != -1){
            mListItems = mDao.getCourseOfStage(this.mStageId);
            setTitle(new StageDAO(mContext).getStageById(this.mStageId).getmTitre());
        }
        if(mListItems != null && !mListItems.isEmpty()) {
            mAdapter = new ListCourseAdapter(this, mListItems);
            mListView.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListCourse.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        this.mListView = (ListView) findViewById(R.id.list_TypeCourse);
        this.mTxtEmptyListCourse = (TextView) findViewById(R.id.txt_empty_list_course);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_COURSE) {
            if(resultCode == RESULT_OK) {

                if(data != null) {
                    Course created = (Course) data.getSerializableExtra(EXTRA_ADDED_COURSE);
                    if(created != null) {
                        if(mListItems == null)
                            mListItems = new ArrayList<Course>();
                        mListItems.add(created);

                        if(mAdapter == null) {
                            if(mListView.getVisibility() != View.VISIBLE) {
                                mListView.setVisibility(View.VISIBLE);
                                mTxtEmptyListCourse.setVisibility(View.GONE);
                            }

                            mAdapter = new ListCourseAdapter(this, mListItems);
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
