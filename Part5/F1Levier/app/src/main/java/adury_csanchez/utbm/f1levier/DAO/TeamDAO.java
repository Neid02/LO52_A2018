package adury_csanchez.utbm.f1levier.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;


public class TeamDAO {

    public static final String TAG = "TeamDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_TEAM_ID,
            DBHelper.COLUMN_TEAM_NAME
    };

    public TeamDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Team createTeam(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TEAM_NAME, name);

        long insertId = mDatabase.insert(DBHelper.TABLE_TEAMS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,DBHelper.COLUMN_TEAM_ID + " = " + insertId,
                null, null,null, null);
        cursor.moveToFirst();
        Team newTeam = cursorToTeam(cursor);
        cursor.close();
        return newTeam;
    }

    public void deleteTeam(Team team) {
        long id = team.getId();
        // TODO : delete all enrolements to this team
        // TODO : delete all subscribtions of this team

        System.out.println("the deleted team has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_TEAMS, DBHelper.COLUMN_TEAM_ID
                + " = " + id, null);
    }

    public List<Team> getAllTeams() {
        List<Team> listTeams = new ArrayList<Team>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Team team = cursorToTeam(cursor);
                listTeams.add(team);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listTeams;
    }

    public Team getTeamById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TEAMS, mAllColumns,
                DBHelper.COLUMN_TEAM_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Team team = cursorToTeam(cursor);
        return team;
    }

    // TODO : to romove
    public List<Team> getTeamsOfRace(long raceId) {
        List<Team> listTeams= new ArrayList<Team>();

        String query="SELECT "
                +   " t." +DBHelper.COLUMN_TEAM_ID +" "
                +   ",t." + DBHelper.COLUMN_TEAM_NAME + " "
                +"FROM "
                +   DBHelper.TABLE_SUBSCRIPTIONS + " AS s INNER JOIN " + DBHelper.TABLE_TEAMS + " AS t "
                +                   "ON s."+ DBHelper.COLUMN_ENROLMENT_TEAM_ID + "=t." + DBHelper.COLUMN_TEAM_ID + " "
                +"WHERE s." + DBHelper.COLUMN_SUBSCRIPTIONS_RACE_ID + "=?" ;

        Cursor cursor = mDatabase.rawQuery(query, new String[] { String.valueOf(raceId) });

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Team team = cursorToTeam(cursor);
            listTeams.add(team);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listTeams;
    }

    protected Team cursorToTeam(Cursor cursor) {
        Team team = new Team();
        team.setId(cursor.getLong(0));
        team.setName(cursor.getString(1));
        return team;
    }
}
