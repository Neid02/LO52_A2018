package fr.utbm.experience.tipstop_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MYSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MANIFESTATION = "T_Manifestation";
    public static final String TABLE_RUNNER = "T_Runner";
    public static final String[] allColumnsRunner = {"r_matricule","r_team", "r_echelon", "r_name"};
    //public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "tipstopbeta.db";
    private static final String TABLE_TEAM = "T_Team";
    private static final int DATABASE_VERSION =19  ;

    // SQL command to create table manifestion in database
    private static final String DATABASE_CREATE_MANIF =  " CREATE TABLE IF NOT EXISTS  T_Manifestation ("
            + " m_Id integer primary key autoincrement,"
            + " m_name text not null,"
            + " m_date date not null,"
            + " m_place text not null"
            +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_TEAM = "CREATE TABLE IF NOT EXISTS  T_Team ("
             + "t_Id integer primary key autoincrement,"
             + "t_nbreParticipant integer not null,"
             + "t_echelonEquipe integer not null,"
             + "t_manifestationTeam integer,"
             + "FOREIGN KEY(t_manifestationTeam) REFERENCES T_Manifestation(m_Id)"
             +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_RUN =  " CREATE TABLE T_Runner ("
            + "r_matricule text primary key,"
            + "r_team integer,"
            + "r_echelon integer ,"
            + "r_name text "
            //+ "FOREIGN KEY(r_team) REFERENCES T_Team(t_Id)"
            +");";
    // SQL command to create table team in database
    private static final String DATABASE_CREATE_RUNTIME =  " CREATE TABLE  T_TimeRunner ("
            + "id integer primary key autoincrement,"
            + "r_RunnerMat text not null,"
            + "t1_Sprint integer not null,"
            + "t1_Fract integer not null,"
            + "t_PitStop integer not null,"
            + "t2_Sprint integer not null,"
            + "t2_Fract integer not null,"
            + "Moy integer not null,"
            + "Passage int not null"
            +");";

    private void addTeam( SQLiteDatabase database)
    {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for(int i=0;i<10;i++)
        {
            values.put("t_nbreParticipant", 0);
            values.put("t_echelonEquipe", 0);

            // Insert the new row, returning the primary key value of the new row
             database.insert("T_Team", null, values);
        }

    }

    private void addRunner( SQLiteDatabase database)
    {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        String[] names = {"Patrick","Alain","Boris","Marcel","Elsa","Ornela","Nelly","Shahid","Paul",
                "Ren","Cora","Pierre","Bill","Nancy","Morelle","Patrice","Ange","Paterson","Micael","Persil"};


        for(int i=0;i<20;i++)
        {
           values.put("r_matricule", "MAT-0"+i);
            values.put("r_team",0);
            values.put("r_echelon",(int)(1 + Math.random()*(5 -1)));
            values.put("r_name", names[i].replace("'","''"));

            // Insert the new row, returning the primary key value of the new row
            database.insert("T_Runner", null, values);
           /* names[i].replace("'","''");

            int ech = (int)(1+Math.random()*(5 -1));
            String strSql = "insert into T_Runner(r_matricule,r_team,r_echelon,r_name) values('" +
                    "MAT-0"+ i +"','"+ 1 +"','"+ 1 +"','y')";

            database.execSQL(strSql);*/
        }

    }


    public MYSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {


        database.execSQL("PRAGMA foreign_keys = ON");


        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RUNNER);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MANIFESTATION);
        database.execSQL("DROP TABLE IF EXISTS " +"T_TimeRunner");
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_RUNNER);
        //database.execSQL("DROP TABLE IF EXISTS " + TABLE_RUNNER);

        database.execSQL( DATABASE_CREATE_MANIF);
        database.execSQL(DATABASE_CREATE_TEAM);
        database.execSQL(DATABASE_CREATE_RUN );
        database.execSQL(DATABASE_CREATE_RUNTIME);
        //database.execSQL("ALTER TABLE "+ TABLE_RUNNER +" ADD "+ "r_teamRunner");
        addTeam(database);
        addRunner(database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MYSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANIFESTATION);
        onCreate(db);
        /*if (oldVersion < 2)
        {
            db.execSQL("ALTER TABLE "+ TABLE_RUNNER +" ADD "+ "r_teamRunner");
         //   db.execSQL("ALTER TABLE "+ TABLE_RUNNER+" ADD CONSTRAINT fk_"+TABLE_RUNNER+"_parent FOREIGN KEY (r_teamRunner)  REFERENCES T_Team(t_Id)");
        }*/



    }
}
