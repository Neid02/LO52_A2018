package lo52.utbm.fr.codept25;

import android.arch.lifecycle.LiveData;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import fr.utbm.lo52.domain.Race;
import fr.utbm.lo52.repository.dao.RaceDao;
import fr.utbm.lo52.repository.dao.CodeptRoomDatabase;

public class MainActivity extends AppCompatActivity {


    private EditText nameInput, dateInput;
    private Button buttonAddData;
    private Button bViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        nameInput=(EditText)findViewById(R.id.name_editext);

        dateInput=(EditText) findViewById(R.id.date_edittext);
        buttonAddData=(Button)findViewById(R.id.add_button);
        bViewData=(Button)findViewById(R.id.read_button) ;*/

        AddData();
        ViewData();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean test;
                Race race = new Race();
                race.setName(nameInput.getText().toString());
                CodeptRoomDatabase db= CodeptRoomDatabase.getInstance(MainActivity.this) ;
                RaceDao raceDAO = db.raceDao();
                raceDAO.insertRace(race);

                /*//test=raceDao.create(race);//
               //boolean isInserted= db.insertData(nameInput.getText().toString(),dateInput.getText().toString());
                if (test==true)
                    Toast.makeText(MainActivity.this,"data inserted succefukky", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"data not inserted ", Toast.LENGTH_LONG).show();*/
            }
        });
    }

    public void ViewData(){
        bViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data;
                LiveData<List<Race>> test;
                List<Race> course;
                CodeptRoomDatabase db= CodeptRoomDatabase.getInstance(MainActivity.this) ;
                RaceDao raceDAO = db.raceDao();
                course=raceDAO.getAllRace();

            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
