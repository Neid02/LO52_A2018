package fr.utbm.experience.tipstop_app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.utbm.experience.tipstop_app.database.MYSQLiteHelper;
import fr.utbm.experience.tipstop_app.model.Manifestation;
import fr.utbm.experience.tipstop_app.model.Runner;

public class RunnerDao  {


    // Champs de la base de données
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    private String[] allColumns = {"r_matricule", "r_name", "r_echelon"};


    public RunnerDao(Context context) {
        dbHelper = new MYSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertRunner(Runner runner){

        runner.setR_matricule(runner.getR_matricule().replace("'","''"));
        runner.setR_name(runner.getR_name().replace("'","''"));


        String strSql = "insert into T_Manifestation(m_name,m_date,m_place) values('" +
                runner.getR_matricule() +"'," + runner.getR_name() +",'" + runner.getR_echelon() +"')";

        database.execSQL(strSql);
        Log.i( "DATABASE", "New runner created");

    }

    public List<Runner> getAllRunner(){

        List<Runner> allManifestation = new ArrayList<Runner>();


        String strSql = "select * from T_Manifestation";

        Cursor cursor = database.query("T_manifestation",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Runner runner = cursorToRunner(cursor);
            allManifestation.add(runner);
            cursor.moveToNext();
        }
        Log.i( "DATABASE", "New manifestation created");
        return allManifestation;
    }
    private Runner cursorToRunner(Cursor cursor) {

        Date simpledate = null;
        try {
            simpledate = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Runner runner = new Runner(cursor.getString(0), cursor.getString(1),cursor.getInt(2));
        return runner;
    }
    public void deleteRunner(Runner runner) {
        String matricule = runner.getR_matricule();
        System.out.println("Runner deleted with id: " + matricule);
        database.delete("T_Runner", "r_matricule"
                + " = " + matricule, null);
    }
}
