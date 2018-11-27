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
import fr.utbm.experience.tipstop_app.model.Team;

public class TeamDao {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    private String[] allColumns = {"t_Id", "t_teamManifestation","t_nbreParticipant", "t_echelonEquipe"};

    public TeamDao(Context context) {

        dbHelper = new MYSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertTeam(Team team){


        String strSql = "insert into T_Team(t_teamManifestation,t_nbreParticipant,t_echelonEquipe) values('" +
                team.getManifestation() +"'," + team.getNbreParticipant() +",'" + team.getEchelon() +"')";

        database.execSQL(strSql);
        Log.i( "DATABASE", "New Team created");

    }

    public List<Team> getAllTeam(){

        List<Team> allTeam = new ArrayList<Team>();


        String strSql = "select * from T_Team";

        Cursor cursor = database.query("T_Team",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Team team = cursorToTeam(cursor);
            allTeam.add(team);
            cursor.moveToNext();
        }
        Log.i( "DATABASE", "New manifestation created");
        return allTeam;
    }
    private Team cursorToTeam(Cursor cursor) {

        Date simpledate = null;

        Team team = new Team(cursor.getInt(0), cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
        return team;
    }
    public void deleteTeam(Team team) {
        long id = team.getId();
        System.out.println("Team deleted with id: " + id);
        database.delete("T_Team", "t_Id"
                + " = " + id, null);
    }
}
