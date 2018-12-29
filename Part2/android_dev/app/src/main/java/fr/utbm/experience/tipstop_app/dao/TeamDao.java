package fr.utbm.experience.tipstop_app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.utbm.experience.tipstop_app.database.MYSQLiteHelper;
import fr.utbm.experience.tipstop_app.model.Team;

public class TeamDao {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MYSQLiteHelper dbHelper;
    //private String[] allColumnsR = {"r_matricule","r_team", "r_name", "r_echelon"};
    private String[] allColumns = {"t_Id", "t_manifestationTeam","t_nbreParticipant", "t_echelonEquipe"};

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


        String strSql = "insert into T_Team(t_manifestationTeam,t_nbreParticipant,t_echelonEquipe) values('" +
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
        Log.i( "DATABASE - getAllTeam", "teams are getting all");
        return allTeam;
    }

    public List<Team> getTeamAvailable(List<Integer> idTeams){
        final Set<Integer> setToReturn = new HashSet();
        final Set<Integer> set1 = new HashSet();
        List<Team> result = new  ArrayList<Team>();

        setToReturn.addAll(idTeams);
        Log.i( "DATABASE - setTo", setToReturn.toString());


        for(Integer intern :setToReturn) {
            Cursor cursor = database.query("T_Team",
                    allColumns, "t_Id ="+intern, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Team team = cursorToTeam(cursor);
                result.add(team);
                cursor.moveToNext();
            }
        }

        // remove duplicate itemns

        //return setToReturn;

        return  result;
    }
    private Team cursorToTeam(Cursor cursor) {


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
