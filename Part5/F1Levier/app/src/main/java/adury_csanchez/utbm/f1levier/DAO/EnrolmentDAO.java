package adury_csanchez.utbm.f1levier.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import adury_csanchez.utbm.f1levier.model.Enrolment;
import adury_csanchez.utbm.f1levier.model.Runner;
import adury_csanchez.utbm.f1levier.model.Team;


public class EnrolmentDAO {

    public static final String TAG = "EnrolmentDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_ENROLMENT_ID,
            DBHelper.COLUMN_ENROLMENT_RUNNER_ID,
            DBHelper.COLUMN_ENROLMENT_TEAM_ID
    };

    public EnrolmentDAO(Context context) {
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

    public Enrolment createEnrolment(long runnerId, long teamId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ENROLMENT_RUNNER_ID, runnerId);
        values.put(DBHelper.COLUMN_ENROLMENT_TEAM_ID, teamId);

        long insertId = mDatabase.insert(DBHelper.TABLE_ENROLMENTS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ENROLMENTS, mAllColumns,DBHelper.COLUMN_ENROLMENT_ID + " = " + insertId,
                null, null,null, null);
        cursor.moveToFirst();
        Enrolment newEnrolment = cursorToEnrolment(cursor);
        cursor.close();
        return newEnrolment;
    }

    public void deleteEnrolment(Enrolment enrolment) {
        long id = enrolment.getId();
        System.out.println("the deleted enrolment has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_ENROLMENTS, DBHelper.COLUMN_ENROLMENT_ID
                + " = " + id, null);
    }

    public List<Enrolment> getAllEnrolments() {
        List<Enrolment> listEnrolments = new ArrayList<Enrolment>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_ENROLMENTS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Enrolment enrolment = cursorToEnrolment(cursor);
                listEnrolments.add(enrolment);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listEnrolments;
    }

    public List<Enrolment> getEnrolmentsOfTeam(long teamId) {
        List<Enrolment> listEnrolments = new ArrayList<Enrolment>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_ENROLMENTS, mAllColumns,
                DBHelper.COLUMN_ENROLMENT_TEAM_ID + " = ?",
                new String[]{String.valueOf(teamId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Enrolment enrolment = cursorToEnrolment(cursor);
            listEnrolments.add(enrolment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listEnrolments;
    }

    public Enrolment getEnrolementById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ENROLMENTS, mAllColumns,
                DBHelper.COLUMN_ENROLMENT_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Enrolment enrolment = cursorToEnrolment(cursor);
        return enrolment;
    }

    protected Enrolment cursorToEnrolment(Cursor cursor) {
        Enrolment enrolment = new Enrolment();
        enrolment.setId(cursor.getLong(0));

        // get the runner by id
        long runnerId = cursor.getLong(1);
        RunnerDAO runnerDAO = new RunnerDAO(mContext);
        Runner runner = runnerDAO.getRunnerById(runnerId);
        if (runner != null)
            enrolment.setRunner(runner);

        // get the team by id
        long teamId = cursor.getLong(2);
        TeamDAO teamDAO = new TeamDAO(mContext);
        Team team = teamDAO.getTeamById(teamId);
        if (team != null)
            enrolment.setTeam(team);

        return enrolment;
    }
}