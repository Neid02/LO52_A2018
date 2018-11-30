package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListStageAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListTypeCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.StageDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;

public class Stages extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "Stages";

    public static final int REQUEST_CODE_ADD_STAGE = 40;
    public static final String EXTRA_ADDED_STAGE = "extra_key_added_stage";

    private ListView mListView;
    private TextView mTxtEmptyListStage;

    private ListStageAdapter mAdapter;
    private List<Stage> mListItem;
    private StageDAO mDao;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddStageActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_STAGE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // initialize views
        initViews();

        // fill the listView
        mDao = new StageDAO(this);
        mListItem = mDao.getAllStage();
        if(mListItem != null && !mListItem.isEmpty()) {
            mAdapter = new ListStageAdapter(this, mListItem);
            mListView.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListStage.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        this.mListView = (ListView) findViewById(R.id.list_Stage);
        this.mTxtEmptyListStage = (TextView) findViewById(R.id.txt_empty_list_Stage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_STAGE) {
            if(resultCode == RESULT_OK) {

                if(data != null) {
                    Stage created = (Stage) data.getSerializableExtra(EXTRA_ADDED_STAGE);
                    if(created != null) {
                        if(mListItem == null)
                            mListItem = new ArrayList<Stage>();
                        mListItem.add(created);

                        if(mAdapter == null) {
                            if(mListView.getVisibility() != View.VISIBLE) {
                                mListView.setVisibility(View.VISIBLE);
                                mTxtEmptyListStage.setVisibility(View.GONE);
                            }

                            mAdapter = new ListStageAdapter(this, mListItem);
                            mListView.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListItem);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stages, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            /*Intent intent = new Intent(this, Stage.class);
            this.startActivity(intent);*/
        } else if (id == R.id.nav_parametre) {
            Intent intent = new Intent(this, ParametresCourses.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
