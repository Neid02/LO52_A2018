package fr.utbm.mamassi_maillard_lo52.codep25v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.adapter.ListTypeCourseAdapter;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;

public class ParametresCourses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "ParametresCourses";

    public static final int REQUEST_CODE_ADD_TYPECOURSE = 40;
    public static final String EXTRA_ADDED_TYPECOURSE = "extra_key_added_type_course";

    private ListView mListView;
    private TextView mTxtEmptyListTypeCourse;

    private ListTypeCourseAdapter mAdapter;
    private List<TypeCourse> mListTypeCourse;
    private TypeCourseDAO mDao;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bouton ajouter
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddTypeCourseActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_TYPECOURSE);
            }
        });
        //=====================================================================================

        //menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //=====================================================================================


        // initialize views
        initViews();

        // fill the listView
        mDao = new TypeCourseDAO(this);
        mListTypeCourse = mDao.getAllTypeCourse();
        if(mListTypeCourse != null && !mListTypeCourse.isEmpty()) {
            mAdapter = new ListTypeCourseAdapter(this, mListTypeCourse);
            mListView.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListTypeCourse.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        this.mListView = (ListView) findViewById(R.id.list_TypeCourse);
        this.mTxtEmptyListTypeCourse = (TextView) findViewById(R.id.txt_empty_list_TypeCourse);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_TYPECOURSE) {
            if(resultCode == RESULT_OK) {

                if(data != null) {
                    TypeCourse created = (TypeCourse) data.getSerializableExtra(EXTRA_ADDED_TYPECOURSE);
                    if(created != null) {
                        if(mListTypeCourse == null)
                            mListTypeCourse = new ArrayList<TypeCourse>();
                        mListTypeCourse.add(created);

                        if(mAdapter == null) {
                            if(mListView.getVisibility() != View.VISIBLE) {
                                mListView.setVisibility(View.VISIBLE);
                                mTxtEmptyListTypeCourse.setVisibility(View.GONE);
                            }

                            mAdapter = new ListTypeCourseAdapter(this, mListTypeCourse);
                            mListView.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListTypeCourse);
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parametres_courses, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, Stages.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        } else if (id == R.id.nav_parametre) {

        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
