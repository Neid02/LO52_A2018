package fr.utbm.lo52.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHandler extends SQLiteOpenHelper {

    /*
     Attribut de la table Course
     */

    public static final String RACE_KEY = "id";
    public static final String RACE_NAME = "name";
    public static final String RACE_DATE = "date";

    /*
    Attribut de la table TeamRunner
    */

    public static final String TEAM_RUNNER_KEY_RUNNER = "idRunner";
    public static final String TEAM_RUNNER_KEY_RACE = "idRace";
    public static final String TEAM_RUNNER_KEY_TEAM = "idTeam";
    public static final String TEAM_RUNNER_ORDER = "orderParticipant";


    /*
    Attribut de la table Participant
    */

    public static final String PARTICIPANT_RUNNER_KEY = "idRunner";
    public static final String PARTICIPANT_RACE_KEY = "idRace";
    public static final String PARTICIPANT_TIME_FIRST_LAP_SPRINT = "timeFirstLapSprint";
    public static final String PARTICIPANT_TIME_SECOND_LAP_SPRINT = "timeSecondLapSprint";
    public static final String PARTICIPANT_TIME_FIRST_LAP_SPLIT = "timeFirstLapSplit";
    public static final String PARTICIPANT_TIME_SECOND_LAP_SPLIT = "timeFirstLapSplit";
    public static final String PARTICIPANT_TIME_PIT_STOP = "timePitStop";

    /*
    Attribut de la table Equipe
    */

    public static final String TEAM_KEY = "idTeam";
    public static final String TEAM_RACE_KEY = "idRace";
    public static final String TEAM_NAME = "name";

    /*
    Attribut de la table Personne
    */

    public static final String RUNNER_KEY = "id";
    public static final String RUNNER_LASTNAME = "lastname";
    public static final String RUNNER_FIRSTNAME = "firstname";
    public static final String RUNNER_ECHELON = "echelon";
    public static final String RUNNER_KEY_LEVEL = "idLevel";


    /*
     Attribut de la table Category
     */

    public static final String CATEGORY_KEY = "id";
    public static final String CATEGORY_LEVEL = "level";

    /*
    Script de Creation de la table course
    */

    public static final String RACE_TABLE_NAME = "Race";
    public static final String RACE_TABLE_CREATE =
            "CREATE TABLE " + RACE_TABLE_NAME + " (" +
                    RACE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RACE_NAME + " TEXT, " +
                    RACE_DATE + " TEXT);";


    /* Script de Creation de la table Equipe*/
    public static final String TEAM_TABLE_NAME = "Team";
    public static final String TEAM_TABLE_CREATE =
            "CREATE TABLE " + TEAM_TABLE_NAME + " (" +
                    TEAM_RACE_KEY + " INTEGER NOT NULL, " +
                    TEAM_KEY + " INTEGER NOT NULL, " +
                    TEAM_NAME + " TEXT, " +
                    "PRIMARY KEY (" +TEAM_KEY+ "," +TEAM_RACE_KEY+" ),"+
                    "FOREIGN KEY("+TEAM_RACE_KEY+") REFERENCES "+RACE_TABLE_NAME+"("+RACE_KEY+"));";


    /* Script de Creation de la table CATEGORY*/

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                    CATEGORY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_LEVEL + " TEXT);";


    /* Script de Creation de la table Personne*/
    public static final String RUNNER_TABLE_NAME = "Runner";
    public static final String RUNNER_TABLE_CREATE =
            "CREATE TABLE " + RUNNER_TABLE_NAME + " (" +
                    RUNNER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RUNNER_KEY_LEVEL + " INTEGER NOT NULL , " +
                    RUNNER_LASTNAME + " TEXT, " +
                    RUNNER_FIRSTNAME + " TEXT, " +
                    RUNNER_ECHELON + " INTEGER,"+
                    "FOREIGN KEY("+ RUNNER_KEY_LEVEL +") REFERENCES "+CATEGORY_TABLE_NAME+"("+CATEGORY_KEY+"));";


    /*
    Script de Creation de la table TeamRunner
    */

    public static final String TEAM_RUNNER_TABLE_NAME = "TeamRunner";
    public static final String TEAM_RUNNER_TABLE_CREATE =
            "CREATE TABLE " + TEAM_RUNNER_TABLE_NAME + " (" +
                    TEAM_RUNNER_KEY_RUNNER + " INTEGER NOT NULL, " +
                    TEAM_RUNNER_KEY_RACE + " INTEGER NOT NULL, " +
                    TEAM_RUNNER_KEY_TEAM + " INTEGER NOT NULL, " +
                    TEAM_RUNNER_ORDER + " INTEGER, " +
                    "PRIMARY KEY (" +TEAM_RUNNER_KEY_RUNNER+ "," +TEAM_RUNNER_KEY_RACE+","+TEAM_RUNNER_KEY_TEAM+" ),"+
                    "FOREIGN KEY("+TEAM_RUNNER_KEY_RUNNER+") REFERENCES "+RUNNER_TABLE_NAME+"("+RUNNER_KEY+"),"+
                    "FOREIGN KEY("+TEAM_RUNNER_KEY_RACE+") REFERENCES "+TEAM_TABLE_NAME+"("+TEAM_RACE_KEY+"),"+
                    "FOREIGN KEY("+TEAM_RUNNER_KEY_TEAM+") REFERENCES "+TEAM_TABLE_NAME+"("+RACE_KEY+"));";


    /*
     Script de Creation de la table Participant
     */

    public static final String PARTICIPANT_TABLE_NAME = "Participant";
    public static final String PARTICIPANT_TABLE_CREATE =
            "CREATE TABLE " + PARTICIPANT_TABLE_NAME + " (" +
                    PARTICIPANT_RUNNER_KEY + " INTEGER NOT NULL, " +
                    PARTICIPANT_RACE_KEY  + " INTEGER NOT NULL, " +
                    PARTICIPANT_TIME_FIRST_LAP_SPRINT  + " INTEGER, " +
                    PARTICIPANT_TIME_SECOND_LAP_SPRINT  + " INTEGER, " +
                    PARTICIPANT_TIME_FIRST_LAP_SPLIT  + " INTEGER, " +
                    PARTICIPANT_TIME_SECOND_LAP_SPLIT  + " INTEGER, " +
                    PARTICIPANT_TIME_PIT_STOP  + " INTEGER, " +
                    "PRIMARY KEY (" +PARTICIPANT_RUNNER_KEY+ "," +PARTICIPANT_RACE_KEY+" ),"+
                    "FOREIGN KEY("+PARTICIPANT_RUNNER_KEY+") REFERENCES "+RUNNER_TABLE_NAME+"("+RUNNER_KEY+"),"+
                    "FOREIGN KEY("+PARTICIPANT_RACE_KEY+") REFERENCES "+RACE_TABLE_NAME+"("+RACE_KEY+"));";



    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RACE_TABLE_CREATE);
        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(RUNNER_TABLE_CREATE);
        db.execSQL(TEAM_TABLE_CREATE);
        db.execSQL(PARTICIPANT_TABLE_CREATE);
        db.execSQL(TEAM_RUNNER_TABLE_CREATE);
    }

    public static final String RACE_TABLE_DROP = "DROP TABLE IF EXISTS " + RACE_TABLE_NAME + ";";

    public static final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME + ";";

    public static final String RUNNER_TABLE_DROP = "DROP TABLE IF EXISTS " + RUNNER_TABLE_NAME + ";";

    public static final String TEAM_TABLE_DROP = "DROP TABLE IF EXISTS " + TEAM_TABLE_NAME + ";";

    public static final String PARTICIPANT_TABLE_DROP = "DROP TABLE IF EXISTS " + PARTICIPANT_TABLE_NAME + ";";

    public static final String TEAM_RUNNER_TABLE_DROP = "DROP TABLE IF EXISTS " + TEAM_RUNNER_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RACE_TABLE_DROP);
        db.execSQL(CATEGORY_TABLE_DROP);
        db.execSQL(RUNNER_TABLE_DROP);
        db.execSQL(TEAM_TABLE_DROP);
        db.execSQL(PARTICIPANT_TABLE_DROP);
        db.execSQL(TEAM_RUNNER_TABLE_DROP);
        onCreate(db);
    }

}

