package adury_csanchez.utbm.f1levier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adury_csanchez.utbm.f1levier.DAO.RunnerDAO;
import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.Runner;

public class AddRunnerActivity extends Activity implements OnClickListener {

    public static final String TAG = "AddRunnerActivity";

    private EditText mTxtRunnerFirstName;
    private EditText mTxtRunnerLastName;
    private EditText mTxtRunnerWeight;
    private Button mBtnAdd;

    private RunnerDAO mRunnerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_runner);

        initViews();

        this.mRunnerDao = new RunnerDAO(this);
    }

    private void initViews() {
        this.mTxtRunnerFirstName = (EditText) findViewById(R.id.txt_create_runner_first_name);
        this.mTxtRunnerLastName = (EditText) findViewById(R.id.txt_create_runner_last_name);
        this.mTxtRunnerWeight = (EditText) findViewById(R.id.txt_create_runner_weight);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add_runner);

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_runner:
                Editable firstName = mTxtRunnerFirstName.getText();
                Editable lastName = mTxtRunnerLastName.getText();
                Editable weight = mTxtRunnerWeight.getText();

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)  && !TextUtils.isEmpty(weight))
                {
                    // add runner to database
                    Runner createdRunner = mRunnerDao.createRunner(firstName.toString(),lastName.toString(),Integer.parseInt(weight.toString()));
                    Log.d(TAG, "added runner : "+ createdRunner.getFistName() + " " + createdRunner.getLastName() +" " + createdRunner.getWeight());

                    // go back to main activity
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.EXTRA_ADDED_RUNNER, createdRunner);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunnerDao.close();
    }
}
