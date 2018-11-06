package com.emilienmoncan.codep25.DataBase.ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.emilienmoncan.codep25.DataBase.MySQLiteDatabase;

public class DBTemps {

    private static final String TABLE = "TEMPS";
    private static final String id_temps = "id_temps";
    private static final int num_id_temps = 0;
    private static final String duree_temps = "duree_temps";
    private static final int num_duree_temps = 1;
    private static final String id_crr_temps = "id_crr_temps";
    private static final int num_id_crr_temps = 2;
    private static final String id_typetour_temps = "id_typetour_temps";
    private static final int num_id_typetour_temps = 3;
    private SQLiteDatabase bdd;
    private MySQLiteDatabase maBaseSQLite;

    public DBTemps(Context context) {
        maBaseSQLite = new MySQLiteDatabase(context, null);
    }

    /**
     * Ouvre la BDD en écriture
     */
    public void open() {
        bdd = maBaseSQLite.getWritableDatabase();
    }

    /**
     * * Ferme l'accès à la BDD
     **/
    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }
}
