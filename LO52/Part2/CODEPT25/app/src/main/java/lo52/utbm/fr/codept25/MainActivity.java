package lo52.utbm.fr.codept25;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.utbm.lo52.domain.Race;
import fr.utbm.lo52.repository.DataBaseHandler;
import fr.utbm.lo52.repository.RaceDao;

public class MainActivity extends AppCompatActivity {

    DataBaseHandler db;
    private EditText nameInput, dateInput;
    private Button buttonAddData;
    private Button bViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db= new DataBaseHandler(this, "lo52codep25",null,1);

        nameInput=(EditText)findViewById(R.id.name_editext);

        dateInput=(EditText) findViewById(R.id.date_edittext);
        buttonAddData=(Button)findViewById(R.id.add_button);
        bViewData=(Button)findViewById(R.id.read_button) ;

        AddData();
        ViewData();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean test;
                RaceDao raceDao = new RaceDao(MainActivity.this);
                Race race = new Race();
                race.setName(nameInput.getText().toString());

                test=raceDao.create(race);
               //boolean isInserted= db.insertData(nameInput.getText().toString(),dateInput.getText().toString());
                if (test==true)
                    Toast.makeText(MainActivity.this,"data inserted succefukky", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"data not inserted ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ViewData(){
        bViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data;
                RaceDao raceDao = new RaceDao(MainActivity.this);
                data=raceDao.allRace();
               // Cursor data= db.getAllData();
                if(data.getCount()==0) {
                    showMessage("Erreur", "No data found");
                    return;
                }
                StringBuffer buffer= new StringBuffer();
                while (data.moveToNext()){
                    buffer.append("Id:"+data.getString(0)+"\n");
                    buffer.append("Name:"+data.getString(1)+"\n");
                    buffer.append("Date:"+data.getString(2)+"\n");
                }
                showMessage("Data", buffer.toString());
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
