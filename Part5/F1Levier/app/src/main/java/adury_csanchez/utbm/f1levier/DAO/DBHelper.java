package adury_csanchez.utbm.f1levier.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // columns of the teams table
    public static final String TABLE_TEAMS = "teams";
    public static final String COLUMN_TEAM_ID = "_id";
    public static final String COLUMN_TEAM_NAME = "name";

    // columns of the runners table
    public static final String TABLE_RUNNERS = "runners";
    public static final String COLUMN_RUNNER_ID = "_id";
    public static final String COLUMN_RUNNER_FIRST_NAME = "first_name";
    public static final String COLUMN_RUNNER_LAST_NAME = "last_name";
    public static final String COLUMN_RUNNER_WEIGHT = "salary";
    //public static final String COLUMN_RUNNER_TEAM_ID = "team_id";

    // columns of the enrolments table
    public static final String TABLE_ENROLMENTS = "enrolments";
    public static final String COLUMN_ENROLMENT_ID = "_id";
    public static final String COLUMN_ENROLMENT_RUNNER_ID = "runner_id";
    public static final String COLUMN_ENROLMENT_TEAM_ID = "team_id";

    // columns of the races table
    public static final String TABLE_RACES = "races";
    public static final String COLUMN_RACE_ID = "_id";
    public static final String COLUMN_RACE_NAME = "name";

    // columns of the subscriptions table
    public static final String TABLE_SUBSCRIPTIONS = "subscriptions";
    public static final String COLUMN_SUBSCRIPTIONS_ID = "_id";
    public static final String COLUMN_SUBSCRIPTIONS_TEAM_ID = "team_id";
    public static final String COLUMN_SUBSCRIPTIONS_RACE_ID = "race_id";

    // columns of the lap time table
    public static final String TABLE_LAP_TIMES = "lap_times";
    public static final String COLUMN_LAP_TIME_ID = "_id";
    public static final String COLUMN_LAP_TIME_RUNNER_ID = "runner_id";
    public static final String COLUMN_LAP_TIME_RACE_ID = "race_id";
    public static final String COLUMN_LAP_TIME_NUMBER = "number";
    public static final String COLUMN_LAP_TIME_SPRINT_1 = "time_sprint_1";
    public static final String COLUMN_LAP_TIME_FRACTIONATED_1 = "time_fractionated_1";
    public static final String COLUMN_LAP_TIME_PIT_STOP = "time_pit_stop";
    public static final String COLUMN_LAP_TIME_SPRINT_2 = "time_sprint_2";
    public static final String COLUMN_LAP_TIME_FRACTIONATED_2 = "time_fractionated_2";



    // Database name and version
    private static final String DATABASE_NAME = "f1levier.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the teams table creation
    private static final String SQL_CREATE_TABLE_TEAMS = "CREATE TABLE " + TABLE_TEAMS + "("
            + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_NAME + " TEXT NOT NULL "
            +");";

    // SQL statement of the runners table creation
    private static final String SQL_CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_RUNNERS + "("
            + COLUMN_RUNNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RUNNER_FIRST_NAME + " TEXT NOT NULL, "
            + COLUMN_RUNNER_LAST_NAME + " TEXT NOT NULL, "
            + COLUMN_RUNNER_WEIGHT + " INTEGER NOT NULL "
         //   + COLUMN_RUNNER_TEAM_ID + " INTEGER NOT NULL "
            +");";

    // SQL statement of the enrolments table creation
    private static final String SQL_CREATE_TABLE_ENROLMENTS = "CREATE TABLE " + TABLE_ENROLMENTS + "("
            + COLUMN_ENROLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ENROLMENT_RUNNER_ID + " INTEGER NOT NULL, "
            + COLUMN_ENROLMENT_TEAM_ID+ " INTEGER NOT NULL "
            +");";

    // SQL statement of the races table creation
    private static final String SQL_CREATE_TABLE_RACES = "CREATE TABLE " + TABLE_RACES + "("
            + COLUMN_RACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RACE_NAME + " TEXT NOT NULL "
            +");";

    // SQL statement of the subscriptions table creation
    private static final String SQL_CREATE_TABLE_SUBSCRIPTIONS = "CREATE TABLE " + TABLE_SUBSCRIPTIONS + "("
            + COLUMN_SUBSCRIPTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SUBSCRIPTIONS_TEAM_ID + " INTEGER NOT NULL, "
            + COLUMN_SUBSCRIPTIONS_RACE_ID+ " INTEGER NOT NULL "
            +");";

    // SQL statement of the lap time table creation
    private static final String SQL_CREATE_TABLE_LAP_TIMES = "CREATE TABLE " + TABLE_LAP_TIMES + "("
            + COLUMN_LAP_TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LAP_TIME_RUNNER_ID + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_RACE_ID + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_SPRINT_1 + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_FRACTIONATED_1 + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_PIT_STOP + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_SPRINT_2 + " INTEGER NOT NULL, "
            + COLUMN_LAP_TIME_FRACTIONATED_2 + " INTEGER NOT NULL "
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_TEAMS);
        database.execSQL(SQL_CREATE_TABLE_EMPLOYEES);
        database.execSQL(SQL_CREATE_TABLE_ENROLMENTS);
        database.execSQL(SQL_CREATE_TABLE_RACES);
        database.execSQL(SQL_CREATE_TABLE_SUBSCRIPTIONS);
        database.execSQL(SQL_CREATE_TABLE_LAP_TIMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUNNERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAP_TIMES);

        // recreate the tables
        onCreate(db);
    }

    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}