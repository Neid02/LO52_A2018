package fr.utbm.experience.tipstop_app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import fr.utbm.experience.tipstop_app.database.MYSQLiteHelper;
import fr.utbm.experience.tipstop_app.model.Manifestation;

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

        String strSql = "insert into T_Manifestation(m_name,m_date,m_place) values('" +
                manifestation.getName() +"'," + manifestation.getDateEvent() +",'" + manifestation.getPlace() +"')";

        database.execSQL(strSql);
        Log.i( "DATABASE", "New manifestation created");

    }

    public List<Manifestation> getAllManifestation(){

        List<Manifestation> allManifestation = new ArrayList<Manifestation>();


        String strSql = "select * from T_Manifestation";

        Cursor cursor = database.query("T_manifestation",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Manifestation manifestation = cursorToManifestation(cursor);
            allManifestation.add(manifestation);
            cursor.moveToNext();
        }
        Log.i( "DATABASE", "New manifestation created");
        return allManifestation;
    }
    private Manifestation cursorToManifestation(Cursor cursor) {

        Date simpledate = null;
        try {
            simpledate = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Manifestation manifestation = new Manifestation(cursor.getInt(0), cursor.getString(1),simpledate,cursor.getString(1));
        return manifestation;
    }
    public void deleteManifestation(Manifestation manifestation) {
        long id = manifestation.getId();
        System.out.println("Manifestion deleted with id: " + id);
        database.delete("T_Manifestation", "m_Id"
                + " = " + id, null);
    }
}
