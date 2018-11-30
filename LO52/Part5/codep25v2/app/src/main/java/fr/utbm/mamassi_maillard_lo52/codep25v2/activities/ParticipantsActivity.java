package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListParticipantAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.CourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.ParticipantDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Course;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;

public class ParticipantsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_PARTICIPANT = 40;
    public static final String EXTRA_ADDED_PARTICIPANT = "extra_key_added_course";
    public static final String EXTRA_SELECTED_STAGE_ID = "extra_key_selected_stage_id";

    private ListView mListView;
    private TextView mTxtEmptyListParticipant;

    private ListParticipantAdapter mAdapter;
    private List<Participant> mListItems;
    private ParticipantDAO mDao;
    Context mContext = this;
    private long mStageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStageId = getIntent().getLongExtra(CoursesActivity.EXTRA_SELECTED_STAGE_ID,-1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddParticipantActivity.class);
                Bundle b = new Bundle();
                b.putLong(EXTRA_SELECTED_STAGE_ID, mStageId);
                intent.putExtras(b);
                startActivityForResult(intent, REQUEST_CODE_ADD_PARTICIPANT);
            }
        });

        // initialize views
        initViews();

        // fill the listView
        mDao = new ParticipantDAO(this);
        if(this.mStageId != -1){
            mListItems = mDao.getParticipantOfStage(this.mStageId);
            setTitle(new StageDAO(mContext).getStageById(this.mStageId).getmTitre());
        }
        if(mListItems != null && !mListItems.isEmpty()) {
            mAdapter = new ListParticipantAdapter(this, mListItems);
            mListView.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListParticipant.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }

    }

        private void initViews() {
            this.mListView = (ListView) findViewById(R.id.list_Participant);
            this.mTxtEmptyListParticipant = (TextView) findViewById(R.id.txt_empty_list_participant);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == REQUEST_CODE_ADD_PARTICIPANT) {
                if(resultCode == RESULT_OK) {

                    if(data != null) {
                        Participant created = (Participant) data.getSerializableExtra(EXTRA_ADDED_PARTICIPANT);
                        if(created != null) {
                            if(mListItems == null)
                                mListItems = new ArrayList<Participant>();
                            mListItems.add(created);

                            if(mAdapter == null) {
                                if(mListView.getVisibility() != View.VISIBLE) {
                                    mListView.setVisibility(View.VISIBLE);
                                    mTxtEmptyListParticipant.setVisibility(View.GONE);
                                }

                                mAdapter = new ListParticipantAdapter(this, mListItems);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }

        return true;
    }

}
