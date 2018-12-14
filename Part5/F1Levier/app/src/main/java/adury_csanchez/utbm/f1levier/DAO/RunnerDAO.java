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

public class RunnerDAO {

    public static final String TAG = "RunnerDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_RUNNER_ID,
            DBHelper.COLUMN_RUNNER_FIRST_NAME,
            DBHelper.COLUMN_RUNNER_LAST_NAME,
            DBHelper.COLUMN_RUNNER_WEIGHT};
            //DBHelper.COLUMN_RUNNER_TEAM_ID};


    public RunnerDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
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

    public Runner createRunner(String firstName, String lastName, int weight) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_RUNNER_FIRST_NAME, firstName);
        values.put(DBHelper.COLUMN_RUNNER_LAST_NAME, lastName);
        values.put(DBHelper.COLUMN_RUNNER_WEIGHT, weight);

        long insertId = mDatabase.insert(DBHelper.TABLE_RUNNERS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RUNNERS, mAllColumns,DBHelper.COLUMN_RUNNER_ID + " = " + insertId,
                null, null,null, null);
        cursor.moveToFirst();
        Runner newRunner = cursorToRunner(cursor);
        cursor.close();
        return newRunner;
    }

    public void deleteRunner(Runner runner) {
        long id = runner.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_RUNNERS, DBHelper.COLUMN_RUNNER_ID
                + " = " + id, null);

        // TODO : delete associated enrolments and laptimes
    }

    public List<Runner> getAllRunners() {
        List<Runner> listRunners = new ArrayList<Runner>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_RUNNERS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Runner runner = cursorToRunner(cursor);
            listRunners.add(runner);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listRunners;
    }

    public Runner getRunnerById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RUNNERS, mAllColumns,
                DBHelper.COLUMN_RUNNER_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Runner runner = cursorToRunner(cursor);
        return runner;
    }

    // TODO : to replace and remove
    public List<Runner> getRunnersOfTeam(long teamId) {
        List<Runner> listRunners= new ArrayList<Runner>();

        String query="SELECT "
                    +   " r." +DBHelper.COLUMN_RUNNER_ID +" "
                    +   ",r." + DBHelper.COLUMN_RUNNER_FIRST_NAME + " "
                    +   ",r." + DBHelper.COLUMN_RUNNER_LAST_NAME + " "
                    +   ",r." + DBHelper.COLUMN_RUNNER_WEIGHT + " "
                    +"FROM "
                    +   DBHelper.TABLE_ENROLMENTS + " AS e INNER JOIN " + DBHelper.TABLE_RUNNERS + " AS r "
                    +                   "ON e."+ DBHelper.COLUMN_ENROLMENT_RUNNER_ID + "=r." + DBHelper.COLUMN_RUNNER_ID + " "
                    +"WHERE e." + DBHelper.COLUMN_ENROLMENT_TEAM_ID + "=?" ;

        Cursor cursor = mDatabase.rawQuery(query, new String[] { String.valueOf(teamId) });


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Runner runner = cursorToRunner(cursor);
            listRunners.add(runner);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listRunners;
    }



    private Runner cursorToRunner(Cursor cursor) {
        Runner runner = new Runner();
        runner.setId(cursor.getLong(0));
        runner.setFirstName(cursor.getString(1));
        runner.setLastName(cursor.getString(2));
        runner.setWeight(cursor.getInt(3));

        return runner;
    }
}
