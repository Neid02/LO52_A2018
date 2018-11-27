package fr.utbm.experience.tipstop_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MYSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MANIFESTATION = "manifestation";
    //public static final String COLUMN_ID_M = "_id";
    //public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "tipstop.db";
    private static final int DATABASE_VERSION = 1;

    // SQL command to create table manifestion in database
    private static final String DATABASE_CREATE_MANIF =  " Create table T_Manifestation ("
            + " m_Id integer primary key autoincrement,"
            + " m_name text not null,"
            + " m_date date not null,"
            + " m_place text not null"
            +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_TEAM = "Create table T_Team ("
             + "t_Id integer primary key autoincrement,"
             + "t_nbreParticipant integer not null,"
             + "t_echelonEquipe integer not null,"
             + "FOREIGN KEY(r_RunnerTeam) REFERENCES T_Manifestation(m_Id)"
             +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_RUN =  " Create table T_Runner ("
            + "r_matricule text primary key not null,"
            + "r_RunnerTeam integer,"
            + "r_name text not null,"
            + "r_echelon integer not null,"
            + "FOREIGN KEY(r_RunnerTeam) REFERENCES T_Team(t_Id)"
            +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_RUNTIME =  " Create table T_TimeRunner ("
            + "id integer primary key autoincrement,"
            + "r_RunnerMat integer not null,"
            + "t1_Sprint integer not null,"
            + "t1_Fract integer not null,"
            + "t_PitStop integer not null,"
            + "t2_Sprint integer not null,"
            + "t2_Fract integer not null,"
            + "Moy integer not null,"
            + "Passage int not null,"
            + "FOREIGN KEY(r_RunnerMat) REFERENCES T_Runner(r_matricule)"
            +");";

    public MYSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_MANIF+DATABASE_CREATE_TEAM+DATABASE_CREATE_RUN+DATABASE_CREATE_RUNTIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MYSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
       /* db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANIFESTATION);
        onCreate(db);*/
    }
}
