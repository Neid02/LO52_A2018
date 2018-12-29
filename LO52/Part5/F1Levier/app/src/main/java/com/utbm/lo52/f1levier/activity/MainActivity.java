package com.utbm.lo52.f1levier.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.entity.Participant;
import com.utbm.lo52.f1levier.fragment.GroupesFragment;
import com.utbm.lo52.f1levier.fragment.HomeFragment;
import com.utbm.lo52.f1levier.fragment.ParticipantsFragment;
import com.utbm.lo52.f1levier.model.GroupeIhm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
        ParticipantsFragment.OnListFragmentInteractionListener, GroupesFragment.OnListFragmentInteractionListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private Fragment fragmentHome;
    private Fragment fragmentParticipants;
    private Fragment fragmentGroupes;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PARTICIPANTS = 1;
    private static final int FRAGMENT_GROUPES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.showFirstFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home :
                this.showFragment(FRAGMENT_HOME);
                break;
            case R.id.nav_participants :
                this.showFragment(FRAGMENT_PARTICIPANTS);
                break;
            case R.id.nav_groupes :
                this.showFragment(FRAGMENT_GROUPES);
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_HOME :
                this.showHomeFragment();
                break;
            case FRAGMENT_PARTICIPANTS :
                this.showParticipantsFragment();
                break;
            case FRAGMENT_GROUPES :
                this.showGroupesFragment();
                break;
            default:
                break;
        }
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_HOME);
            // 1.2 - Mark as selected the menu item corresponding to HomeFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    private void showHomeFragment(){
        if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
        this.startTransactionFragment(this.fragmentHome);

    }

    private void showParticipantsFragment(){
        if (this.fragmentParticipants == null) this.fragmentParticipants = ParticipantsFragment.newInstance();
        this.startTransactionFragment(this.fragmentParticipants);
    }

    private void showGroupesFragment(){
        if (this.fragmentGroupes == null) this.fragmentGroupes = GroupesFragment.newInstance();
        this.startTransactionFragment(this.fragmentGroupes);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onListFragmentInteraction(Participant participant) {

    }


    @Override
    public void onListFragmentInteraction(GroupeIhm groupeIhm) {

    }

}
