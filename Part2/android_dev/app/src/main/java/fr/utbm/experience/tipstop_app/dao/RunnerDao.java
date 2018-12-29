package fr.utbm.experience.tipstop_app.dao;

import android.content.ContentValues;
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
import fr.utbm.experience.tipstop_app.model.Team;

public class RunnerDao  {


    // Champs de la base de données
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    private String[] allColumns = {"r_matricule","r_team", "r_name", "r_echelon"};


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


        String strSql = "insert into T_Runner(r_name,r_runnerTeam,r_echelon) values('" + runner.getR_matricule() + ",'"
                + runner.getR_name() +",'" + runner.getTeam() + ",'" + runner.getR_echelon()+"')";

        database.execSQL(strSql);
        Log.i( "DATABASE", "New runner created");

    }

    public List<Runner> getAllRunner(){

        List<Runner> allRunner = new ArrayList<Runner>();

        String selection = "r_team = ?";
        String[] selectionArg = {"0"};

        String strSql = "select * from T_Runner";

        Cursor cursor = database.query("T_Runner",
                allColumns, selection, selectionArg, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Runner runner = cursorToRunner(cursor);
            allRunner.add(runner);
            cursor.moveToNext();
        }
       // database.delete("T_Runner", null, null);
        Log.i( "DATABASE - getAllRunner", allRunner.toString());
        return allRunner;
    }
    public int getEchelonByMatricule(String matricule)
    {

        //String strSql = "select * from T_Runner where T_matricule = MAT-00";

        Cursor cursor = database.query("T_Runner",
                allColumns, "r_matricule = '"+matricule+"'", null, null, null, null, null);
        Runner runner= null;
        cursor.moveToFirst();
        runner = cursorToRunner(cursor);
        return runner.getR_echelon();
    }
    public Runner getRunnerByMatricule(String matricule)
    {

        //String strSql = "select * from T_Runner where T_matricule = MAT-00";

        Cursor cursor = database.query("T_Runner",
                allColumns, "r_matricule = '"+matricule+"'", null, null, null, null, null);
        Runner runner= null;
        cursor.moveToFirst();
        runner = cursorToRunner(cursor);
        return runner;
    }

    public void insertTeamRunner(int teamID, String matricule)
    {


        ContentValues value = new ContentValues();
        value.put("r_team",teamID);
        String selection = "r_matricule like ?";
        String[] selectionArg = {matricule};

        Log.i("runner - team",Integer.toString(teamID));
        Log.i("runner - matricule",matricule);

        database.update("T_Runner",value,selection,selectionArg);

         String strSql = "select * from T_Runner where T_matricule like"+ matricule;
        Cursor cursor = database.query("T_Runner",
                allColumns, "r_matricule = '"+matricule+"'", null, null, null, null, null);

        cursor.moveToFirst();
        Runner runner = cursorToRunner(cursor);
        Log.i("runner-update",runner.toString());
    }
    public int getAverageEchelon(){

        // compute the average of echelon
        List<Runner> allManifestation = new ArrayList<Runner>();
        int echelonTotal=0;
        int nbreRunner=0;

        String strSql = "select * from T_Runner";
        Cursor cursor = database.query("T_Runner",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Runner runner = cursorToRunner(cursor);
            echelonTotal += runner.getR_echelon();
            nbreRunner++;
            allManifestation.add(runner);
            cursor.moveToNext();
        }

        int averageEchelon = echelonTotal/nbreRunner;
        return averageEchelon*3;
    }

    private Runner cursorToRunner(Cursor cursor) {
        /*Log.i( "DATABASE - matricule",cursor.getString(0));
        Log.i( "DATABASE - team",cursor.getString(1));
        Log.i( "DATABASE - name",cursor.getString(2));
        Log.i( "DATABASE - echelon",Integer.toString(cursor.getInt(3)));*/
        Runner runner = new Runner(cursor.getString(0), cursor.getInt(1),cursor.getInt(3),cursor.getString(2));
        return runner;
    }

    public List<Runner> getAvailableRunner(int r_team)
    {
        List<Runner> allRunner = new ArrayList<Runner>();

        Cursor cursorRunner = database.query("T_Runner",
                allColumns, "r_team ="+r_team, null, null, null, null);

        cursorRunner.moveToFirst();
        while (!cursorRunner.isAfterLast()) {

            Runner runner = cursorToRunner(cursorRunner);
            allRunner.add(runner);
            cursorRunner.moveToNext();
        }

        return allRunner;
    }

    public List<Integer> getAllTeamAvailable()
    {
        List<Integer> allTeam = new ArrayList<Integer>();

        //String strSql = "select sum(r_echelon),t_team from T_Runner groupBy t_team"+ matricule;

        Cursor cursorTeam = database.query("T_Runner",
                new String[]{"r_team"}, "", null, null, null, null);

        cursorTeam.moveToFirst();
        while (!cursorTeam.isAfterLast()) {

            allTeam.add(cursorTeam.getInt(0));
            cursorTeam.moveToNext();
        }
        //List<Integer> echelonList = new ArrayList<Integer>();

        // find echelon by team
        Cursor cursorEchelon = database.query("T_Runner",
                new String[]{"r_team","sum(r_echelon)","count(r_matricule)"}, "", null, new String("r_team"), null, null);

        cursorEchelon.moveToFirst();
        while (!cursorEchelon.isAfterLast()) {

            String strSql = "UPDATE T_Team SET t_echelonEquipe = "+cursorEchelon.getInt(1)+", t_nbreParticipant = "+cursorEchelon.getInt(2)
                    + " WHERE t_Id = "+cursorEchelon.getInt(0);
            database.execSQL(strSql);

            cursorEchelon.moveToNext();
        }


        Log.i( "DATABASE-idTeams", allTeam.toString());

        return  allTeam;
    }
    public void deleteRunner(Runner runner) {
        String matricule = runner.getR_matricule();
        System.out.println("Runner deleted with id: " + matricule);
        database.delete("T_Runner", "r_matricule"
                + " = " + matricule, null);
    }
}
