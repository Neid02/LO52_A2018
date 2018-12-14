package adury_csanchez.utbm.f1levier.DAO;

import java.util.ArrayList;
import java.util.List;

import android.app.LauncherActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import adury_csanchez.utbm.f1levier.model.LapTime;
import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Runner;


public class LapTimeDAO {

    public static final String TAG = "LapTimeDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_LAP_TIME_ID,
            DBHelper.COLUMN_LAP_TIME_RUNNER_ID,
            DBHelper.COLUMN_LAP_TIME_RACE_ID,
            DBHelper.COLUMN_LAP_TIME_NUMBER,
            DBHelper.COLUMN_LAP_TIME_SPRINT_1,
            DBHelper.COLUMN_LAP_TIME_FRACTIONATED_1,
            DBHelper.COLUMN_LAP_TIME_PIT_STOP,
            DBHelper.COLUMN_LAP_TIME_SPRINT_2,
            DBHelper.COLUMN_LAP_TIME_FRACTIONATED_2
    };

    public LapTimeDAO(Context context) {
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

    public LapTime createLapTime(long runnerId, long raceId, int lapNumber, long timeSprint1, long timeFractionated1, long timePitStop, long timeSprint2, long timeFractionated2){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_LAP_TIME_RUNNER_ID, runnerId);
        values.put(DBHelper.COLUMN_LAP_TIME_RACE_ID, raceId);
        values.put(DBHelper.COLUMN_LAP_TIME_NUMBER, lapNumber);
        values.put(DBHelper.COLUMN_LAP_TIME_SPRINT_1, timeSprint1);
        values.put(DBHelper.COLUMN_LAP_TIME_FRACTIONATED_1, timeFractionated1);
        values.put(DBHelper.COLUMN_LAP_TIME_PIT_STOP, timePitStop);
        values.put(DBHelper.COLUMN_LAP_TIME_SPRINT_2, timeSprint2);
        values.put(DBHelper.COLUMN_LAP_TIME_FRACTIONATED_2, timeFractionated2);

        long insertId = mDatabase.insert(DBHelper.TABLE_LAP_TIMES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LAP_TIMES, mAllColumns,
                DBHelper.COLUMN_LAP_TIME_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        LapTime newLapTime = cursorToLapTime(cursor);
        cursor.close();
        return newLapTime;
    }

    public void deleteLapTime(LapTime lapTime) {
        long id = lapTime.getId();
        System.out.println("the deleted lap time has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_LAP_TIMES, DBHelper.COLUMN_LAP_TIME_ID
                + " = " + id, null);
    }

    public List<LapTime> getAllLapTime() {
        List<LapTime> listLapTimes = new ArrayList<LapTime>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_LAP_TIMES, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LapTime lapTime = cursorToLapTime(cursor);
            listLapTimes.add(lapTime);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listLapTimes;
    }


    public List<LapTime> getLapTimesOfRunner(long runnerId) {
        List<LapTime> listLapTimes = new ArrayList<LapTime>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_LAP_TIMES, mAllColumns,
                DBHelper.COLUMN_LAP_TIME_RUNNER_ID + " = ?",
                new String[] { String.valueOf(runnerId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LapTime lapTime = cursorToLapTime(cursor);
            listLapTimes.add(lapTime);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listLapTimes;
    }

    private LapTime cursorToLapTime(Cursor cursor) {
        LapTime lapTime = new LapTime();
        lapTime.setId(cursor.getLong(0));

        // get the runner by id
        long runnerId = cursor.getLong(1);
        RunnerDAO runnerDAO = new RunnerDAO(mContext);
        Runner runner = runnerDAO.getRunnerById(runnerId);
        if (runner != null)
            lapTime.setAuthor(runner);

        // get the race by id
        long raceId = cursor.getLong(2);
        RaceDAO raceDAO = new RaceDAO(mContext);
        Race race = raceDAO.getRaceById(raceId);
        if (runner != null)
            lapTime.setRace(race);
        lapTime.setLapNumber(cursor.getInt(3));
        lapTime.setTimeSprint1(cursor.getLong(4));
        lapTime.setTimeFractionated1(cursor.getLong(5));
        lapTime.setTimePitStop(cursor.getLong(6));
        lapTime.setTimeSprint2(cursor.getLong(7));
        lapTime.setTimeFractionated2(cursor.getLong(8));

        return lapTime;
    }
    public List<LapTime> getLapTimesOfRunnerForRace(long runnerId, long raceId) {
        List<LapTime> listLapTimes = new ArrayList<LapTime>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_LAP_TIMES, mAllColumns,
                DBHelper.COLUMN_LAP_TIME_RUNNER_ID + " = ? AND " + DBHelper.COLUMN_LAP_TIME_RACE_ID + " = ?",
                new String[] { String.valueOf(runnerId), String.valueOf(raceId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LapTime lapTime = cursorToLapTime(cursor);
            listLapTimes.add(lapTime);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listLapTimes;
    }
}
