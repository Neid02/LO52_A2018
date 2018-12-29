package fr.utbm.experience.tipstop_app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import fr.utbm.experience.tipstop_app.database.MYSQLiteHelper;
import fr.utbm.experience.tipstop_app.model.Manifestation;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ManifestationDao {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    private String[] allColumns = {"m_Id", "m_name", "m_date", "m_place"};

    public ManifestationDao(Context context) {
        dbHelper = new MYSQLiteHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertManifestation(Manifestation manifestation){

        manifestation.setName(manifestation.getName().replace("'","''"));
        manifestation.setPlace(manifestation.getPlace().replace("'","''"));
        if(manifestation.getDateEvent()==null)
        {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            manifestation.setDateEvent(strDate);
        }
        String strSql = "insert into T_Manifestation(m_name,m_date,m_place) values('" +
                manifestation.getName() +"','"+ manifestation.getDateEvent() +"','" + manifestation.getPlace() +"')";

        database.execSQL(strSql);
        Log.i( "DATABASE", "New manifestation created");

    }

    public List<Manifestation> getAllManifestation(){

        List<Manifestation> allManifestation = new ArrayList<Manifestation>();

        String del = " Delete from T_Manifestation";
        String strSql = "select * from T_Manifestation";
        //database.delete("T_Manifestation", null, null);
        Cursor cursor = database.query("T_manifestation",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Manifestation manifestation = cursorToManifestation(cursor);
            allManifestation.add(manifestation);
            cursor.moveToNext();
        }
        Log.i( "DATABASE", "Manifestation are getting on");
        return allManifestation;
    }
    private Manifestation cursorToManifestation(Cursor cursor) {

        String simpledate = null;
       /* try {
            simpledate = (java.util.Date)new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Manifestation manifestation = new Manifestation(cursor.getInt(0), cursor.getString(1),  cursor.getString(2),cursor.getString(3));
        return manifestation;
    }
    public void deleteManifestation(Manifestation manifestation) {
        long id = manifestation.getId();
        System.out.println("Manifestion deleted with id: " + id);
        database.delete("T_Manifestation", "m_Id"
                + " = " + id, null);
    }
}
