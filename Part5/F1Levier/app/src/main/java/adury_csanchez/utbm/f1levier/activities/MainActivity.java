package adury_csanchez.utbm.f1levier.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adury_csanchez.utbm.f1levier.DAO.RaceDAO;
import adury_csanchez.utbm.f1levier.DAO.RunnerDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.adapters.ListRunnersAdapter;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.utils.RandomNames;

public class MainActivity extends AppCompatActivity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListRunnersActivity";

    public static final int REQUEST_CODE_ADD_RUNNER = 40;
    public static final String EXTRA_ADDED_RUNNER = "extra_key_added_runner";

    private ListView mListviewRunners;
    private TextView mTxtEmptyListRunners;
    private ImageButton mBtnAddRunner;

    private ListRunnersAdapter mAdapter;
    private List<Runner> mListRunners;
    private RunnerDAO mRunnerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set action bar title
        getSupportActionBar().setTitle(R.string.new_race_title);

        //=================================================================
        //              Data generation
        //=================================================================

        // TODO  =====  <  To remove once first activity is done
        this.deleteDatabase("f1levier.db");

        mRunnerDao = new RunnerDAO(this);

        for(int i = 0;i<5;i++)
            mRunnerDao.createRunner(RandomNames.getRandomFirstName(),RandomNames.getRandomLastName(),new Random().nextInt(100));

        // TODO To remove \> =====================================

        // initialize views
        initViews();

        // fill the listView
        mListRunners = mRunnerDao.getAllRunners();
        if(mListRunners != null && !mListRunners.isEmpty()) {
            mAdapter = new ListRunnersAdapter(this, mListRunners);
            mListviewRunners.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListRunners.setVisibility(View.VISIBLE);
            mListviewRunners.setVisibility(View.GONE);
        }


        final EditText editTextRaceName = (EditText) findViewById(R.id.edittext_race_name);
        Button buttonGoToRace= (Button) findViewById(R.id.btn_go_to_race);

        buttonGoToRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Runner> selectedRunners=new ArrayList<>();
                int selectionSize=0;
                for (int i = 0; i < mAdapter.getItems().size(); i++) {
                    if (mAdapter.isChecked(i)) {
                        selectedRunners.add(mAdapter.getItem(i));
                        selectionSize++;
                    }
                }
                Log.d(TAG, "Selection size="+selectionSize);
                for(Runner r : selectedRunners)
                    Log.d(TAG, "selectedItem : " + r.getFistName() + " " + r.getLastName());

                if(selectionSize<4)
                {
                    Toast.makeText(MainActivity.this,  R.string.select_at_least_4_runners_to_start_a_race, Toast.LENGTH_LONG).show();
                }
                else {
                    if (editTextRaceName.getText().toString().isEmpty())
                    {
                        Toast.makeText(MainActivity.this, R.string.please_set_the_race_name, Toast.LENGTH_LONG).show();
                    }
                    else {
                        // todo : create teams,
                        //Long raceID = getIntent().getExtras().getLong("RaceID");

                        RaceDAO raceDAO = new RaceDAO(MainActivity.this);
                        Race race = raceDAO.createRace(editTextRaceName.getText().toString());
                        race.createWeightedTeamsForRace(MainActivity.this, selectedRunners);

                        Intent intent = new Intent(MainActivity.this, RaceActivity.class);
                        intent.putExtra("RaceID", new Long(race.getId()));
                        startActivity(intent);
                    }
                }


            }
        });

        final CheckBox c= (CheckBox) findViewById(R.id.checkbox_runner_select_all);
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

             @Override
             public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                 for (int i = 0; i < mAdapter.getItems().size(); i++) {
                     mAdapter.setChecked(i,isChecked);
                 }
                 if(isChecked)
                     c.setText(R.string.unselect_all);
                 else
                     c.setText(R.string.select_all);
                 mAdapter.notifyDataSetChanged();
             }
         }
        );
    }

    private void initViews() {
        this.mListviewRunners = (ListView) findViewById(R.id.list_runners);
        this.mTxtEmptyListRunners = (TextView) findViewById(R.id.txt_empty_list_runners);
        this.mBtnAddRunner = (ImageButton) findViewById(R.id.btn_add_runner);
        this.mListviewRunners.setOnItemClickListener(this);
        this.mListviewRunners.setOnItemLongClickListener(this);
        this.mBtnAddRunner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_runner:
                Intent intent = new Intent(this, AddRunnerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_RUNNER);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_RUNNER) {
            if(resultCode == RESULT_OK) {
                // add the added company to the listCompanies and refresh the listView
                if(data != null) {
                    Runner createdRunner = (Runner) data.getSerializableExtra(EXTRA_ADDED_RUNNER);
                    if(createdRunner != null) {
                        if(mListRunners == null)
                            mListRunners = new ArrayList<Runner>();
                        mListRunners.add(createdRunner);

                        if(mAdapter == null) {
                            if(mListviewRunners.getVisibility() != View.VISIBLE) {
                                mListviewRunners.setVisibility(View.VISIBLE);
                                mTxtEmptyListRunners.setVisibility(View.GONE);
                            }

                            mAdapter = new ListRunnersAdapter(this, mListRunners);
                            mListviewRunners.setAdapter(mAdapter);
                        }
                        else {
                            mListviewRunners.setVisibility(View.VISIBLE);
                            mTxtEmptyListRunners.setVisibility(View.GONE);
                            mAdapter.setItems(mListRunners);
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
        mRunnerDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Runner clickedRunner = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : " + clickedRunner.getFistName() + clickedRunner.getLastName());
        showDeleteDialogConfirmation(clickedRunner, position);
        return true;
    }

    private void showDeleteDialogConfirmation(final Runner clickedRunner, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(R.string.delete);
        alertDialogBuilder.setMessage(getResources().getString(R.string.are_you_sure_you_want_to_delete_the_runner) + " \""+clickedRunner.getFistName() + " " + clickedRunner.getLastName() + "\" ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                if(mRunnerDao != null) {
                    mRunnerDao.deleteRunner(clickedRunner);
                    mListRunners.remove(clickedRunner);

                    mAdapter.removeSelected(position);

                    //refresh the listView
                    if(mListRunners.isEmpty()) {
                        mListviewRunners.setVisibility(View.GONE);
                        mTxtEmptyListRunners.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListRunners);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(MainActivity.this, R.string.runner_deleted_successfully, Toast.LENGTH_SHORT).show();
            }
        });

        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }
}
