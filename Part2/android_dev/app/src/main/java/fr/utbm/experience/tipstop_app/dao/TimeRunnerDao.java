package fr.utbm.experience.tipstop_app.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;

import android.util.Log;

import fr.utbm.experience.tipstop_app.database.MYSQLiteHelper;
import fr.utbm.experience.tipstop_app.model.Team;
import fr.utbm.experience.tipstop_app.model.TimeRunner;

public class TimeRunnerDao {
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    private String[] allColumns = {"id", "r_RunnerMat","t1_Sprint", "t1_Fract","t_PitStop","t2_Sprint","t2_Fract","Moy","Passage"};
    public TimeRunnerDao(Context context) {

        dbHelper = new MYSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertTimeRunner(TimeRunner timeRunner){

        String strSql = "insert into T_Runner(t1_Sprint,t1_Fract,t_PitStop,t2_Sprint,t2_Fract, Moy, Passage) values(" +
                timeRunner.getT1_Sprint() +"," + timeRunner.getT1_Fract() +","+ timeRunner.getT1_PitStop() +","
                    + timeRunner.getT2_Sprint() +"," + timeRunner.getT2_Fract() +"," + timeRunner.getMoy() +"," + timeRunner.getPassage() +")";


        database.execSQL(strSql);
        Log.i( "DATABASE", "New timeRunner set");

    }

    public TimeRunner getTimeRunner(String r_matricule){

        //String strSql = "select * from T_Team where id ="+ id;

        Cursor cursor = database.query("T_TimeRunner",
                allColumns, "r_RunnerMat like '"+r_matricule+"'", null, null, null, null);

        TimeRunner timeRunner;
        cursor.moveToFirst();
        if(cursor.getCount()==0) {
             timeRunner = new TimeRunner(r_matricule,0,0,0,0,0,0,0);
        }else {
             timeRunner = new TimeRunner(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
        }

       // Log.i( "DATABASE", "New Time runner saved");
        return timeRunner;
    }

    public TimeRunner setTimeRunner(TimeRunner timeRunner){

        /*String strSql = "update from set " +
                " r_RunnerMat =" + timeRunner.getR_RunnerMat()+"," +
                " t1_Sprint =" + timeRunner.getT1_Sprint()+"," +
                " t1_Fract =" + timeRunner.getT1_Fract()+"," +
                " t_PitStop =" + timeRunner.getT1_PitStop()+"," +
                " t2_Sprint =" + timeRunner.getT2_Sprint()+"," +
                " t2_Fract =" + timeRunner.getT2_Fract()+"," +
                " Moy =" + timeRunner.getMoy()+"," +
                " Passage =" + timeRunner.getPassage() +
                "where id ="+ timeRunner.getId();*/

        ContentValues value = new ContentValues();
        value.put("r_RunnerMat",timeRunner.getR_RunnerMat());
        value.put("t1_Sprint",timeRunner.getT1_Sprint());
        value.put("t1_Fract",timeRunner.getT1_Fract());
        value.put("t_PitStop",timeRunner.getT1_PitStop());
        value.put("t2_Sprint",timeRunner.getT2_Sprint());
        value.put("t2_Fract",timeRunner.getT2_Fract());
        value.put("Moy",timeRunner.getMoy());
        value.put("Passage",timeRunner.getPassage());

        int nbre = database.update("T_TimeRunner", value, "where id ="+timeRunner.getId(), null);

        Log.i( "DATABASE", nbre + " New Time runner saved");
        return timeRunner;
    }


    public void deleteTime(TimeRunner timeRunner) {
        long id = timeRunner.getId();
        System.out.println("Time Runner deleted with id: " + id);
        database.delete("T_TimeRunner", "id"
                + " = " + id, null);
    }


}
