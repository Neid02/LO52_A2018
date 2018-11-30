package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // columns of the TypeCourse table
    public static final String TABLE_TypeCourse                     = "TypeCourse";
    public static final String COLUMN_TypeCourse_idTypeCourse       = "idTypeCourse";
    public static final String COLUMN_TypeCourse_titreTypeCourse    = "titre";

    // columns of the TypeTour table
    public static final String TABLE_TypeTour                       = "TypeTour";
    public static final String COLUMN_TypeTour_idTypeTour           = "idTypeTour";
    public static final String COLUMN_TypeTour_titreTypeTour        = "titre";
    public static final String COLUMN_TypeTour_ordreTypeTour        = "ordre";
    public static final String COLUMN_TypeTour_idTypeCourse         = COLUMN_TypeCourse_idTypeCourse;

    // columns of the Stage table
    public static final String TABLE_Stage                          = "Stage";
    public static final String COLUMN_Stage_idStage                 = "idStage";
    public static final String COLUMN_Stage_Titre                   = "titre";

    // columns of the Participant table
    public static final String TABLE_Participant                    = "Participant";
    public static final String COLUMN_Participant_idParticipant     = "idParticipant";
    public static final String COLUMN_Participant_nom               = "nom";
    public static final String COLUMN_Participant_prenom            = "prenom";
    public static final String COLUMN_Participant_echelon           = "echelon";
    public static final String COLUMN_Participant_idStage           = COLUMN_Stage_idStage;

    // columns of the Course table
    public static final String TABLE_Course                         = "Course";
    public static final String COLUMN_Course_idCourse               = "idCourse";
    public static final String COLUMN_Course_titre                  = "titre";
    public static final String COLUMN_Course_date                   = "date";
    public static final String COLUMN_Course_idStage                = COLUMN_Stage_idStage;
    public static final String COLUMN_Course_idTypeCourse           = COLUMN_TypeCourse_idTypeCourse;

    // columns of the Equipe table
    public static final String TABLE_Equipe                         = "Equipe";
    public static final String COLUMN_Equipe_idEquipe               = "idEquipe";
    public static final String COLUMN_Equipe_nomEquipe              = "nomEquipe";
    public static final String COLUMN_Equipe_idCourse               = COLUMN_Course_idCourse;

    // columns of the ParticipantParEquipe table
    public static final String TABLE_ParticipantParEquipe                 = "ParticipantParEquipe";
    public static final String COLUMN_ParticipantParEquipe_idPartEquipe   = "idPartEquipe";
    public static final String COLUMN_ParticipantParEquipe_ordrePassage   = "ordrePassage";
    public static final String COLUMN_ParticipantParEquipe_groupe         = "groupe";
    public static final String COLUMN_ParticipantParEquipe_idParticipant  = COLUMN_Participant_idParticipant;
    public static final String COLUMN_ParticipantParEquipe_idEquipe       = COLUMN_Equipe_idEquipe;

    // columns of the TypeTour table
    public static final String TABLE_Tour                           = "Tour";
    public static final String COLUMN_Tour_idTour                   = "idTour";
    public static final String COLUMN_Tour_temps                    = "temps";
    public static final String COLUMN_Tour_idTypeTour               = COLUMN_TypeTour_idTypeTour;
    public static final String COLUMN_Tour_idPartParEquipe          = COLUMN_ParticipantParEquipe_idPartEquipe;

    private static final String DATABASE_NAME = "Codep25BDD";
    private static final int DATABASE_VERSION = 4;


    private static final String CREATE_TABLE_TypeCourse = "CREATE TABLE IF NOT EXISTS " + TABLE_TypeCourse + "("
            + COLUMN_TypeCourse_idTypeCourse    + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TypeCourse_titreTypeCourse + " VARCHAR NOT NULL "
            + ");";
    private static final String CREATE_TABLE_TypeTour = "CREATE TABLE IF NOT EXISTS " + TABLE_TypeTour +"("
            + COLUMN_TypeTour_idTypeTour        + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TypeTour_titreTypeTour     + " VARCHAR NOT NULL, "
            + COLUMN_TypeTour_ordreTypeTour     + " INTEGER NOT NULL, "
            + COLUMN_TypeTour_idTypeCourse      + " INTEGER NOT NULL "
            + ");";
    private static final String CREATE_TABLE_Stage = "CREATE TABLE IF NOT EXISTS " + TABLE_Stage + " ("
            + COLUMN_Stage_idStage              + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Stage_Titre              + " VARCHAR"
            + ");";
    private static final String CREATE_TABLE_Participant = "CREATE TABLE IF NOT EXISTS " + TABLE_Participant + " ("
            + COLUMN_Participant_idParticipant  + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Participant_nom            + " VARCHAR, "
            + COLUMN_Participant_prenom         + " VARCHAR, "
            + COLUMN_Participant_echelon        + " INTEGER, "
            + COLUMN_Participant_idStage        + " INTEGER "
            + ");";
    private static final String CREATE_TABLE_Course = "CREATE TABLE IF NOT EXISTS " + TABLE_Course + " ("
            + COLUMN_Course_idCourse            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Course_titre               + " VARCHAR, "
            + COLUMN_Course_date                + " VARCHAR, "
            + COLUMN_Course_idStage             + " INTEGER, "
            + COLUMN_Course_idTypeCourse        + " INTEGER "
            + ");";
    private static final String CREATE_TABLE_Equipe = "CREATE TABLE IF NOT EXISTS " + TABLE_Equipe + " ("
            + COLUMN_Equipe_idEquipe            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Equipe_nomEquipe           + " VARCHAR, "
            + COLUMN_Equipe_idCourse            + " INTEGER "
            + ");";
    private static final String CREATE_TABLE_ParticipantParEquipe = "CREATE TABLE IF NOT EXISTS " + TABLE_ParticipantParEquipe + " ("
            + COLUMN_ParticipantParEquipe_idPartEquipe      + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ParticipantParEquipe_ordrePassage      + " INTEGER, "
            + COLUMN_ParticipantParEquipe_groupe            + " VARCHAR, "
            + COLUMN_ParticipantParEquipe_idParticipant     + " INTEGER, "
            + COLUMN_ParticipantParEquipe_idEquipe          + " INTEGER "
            + ");";
    private static final String CREATE_TABLE_Tour = "CREATE TABLE IF NOT EXISTS " + TABLE_Tour +"("
            + COLUMN_Tour_idTour                + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Tour_temps                 + " VARCHAR NOT NULL, "
            + COLUMN_Tour_idTypeTour            + " INTEGER NOT NULL, "
            + COLUMN_Tour_idPartParEquipe       + " INTEGER NOT NULL "
            + ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_TypeCourse);
        database.execSQL(CREATE_TABLE_TypeTour);
        database.execSQL(CREATE_TABLE_Stage);
        database.execSQL(CREATE_TABLE_Participant);
        database.execSQL(CREATE_TABLE_Course);
        database.execSQL(CREATE_TABLE_Equipe);
        database.execSQL(CREATE_TABLE_ParticipantParEquipe);
        database.execSQL(CREATE_TABLE_Tour);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,"Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tour);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ParticipantParEquipe);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Equipe);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Course);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Participant);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Stage);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TypeTour);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TypeCourse);

        // recreate the tables
        onCreate(db);
    }

    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}