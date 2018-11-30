package com.silentpangolin.codep25.DataBase.ORM;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.silentpangolin.codep25.DataBase.MySQLiteDatabase;
import com.silentpangolin.codep25.Objects.Temps;

import java.util.ArrayList;

public class DBTemps {

    private static final String TABLE = "TEMPS";
    private static final String id_temps = "id_temps";
    private static final int num_id_temps = 0;
    private static final String duree_temps = "duree_temps";
    private static final int num_duree_temps = 1;
    private static final String id_crr_temps = "id_crr_temps";
    private static final int num_id_crr_temps = 2;
    private static final String id_equ_temps = "id_equ_temps";
    private static final int num_id_equ_temps = 3;
    private static final String id_typetour_temps = "id_typetour_temps";
    private static final int num_id_typetour_temps = 4;
    private static final String date_temps = "date_temps";
    private static final int num_date_temps = 5;
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

    public void insertTemps(ArrayList<Temps> temps){
        ContentValues values;
        for(Temps t : temps){
            values = new ContentValues();
            values.put(duree_temps, t.getDuree_temps());
            values.put(id_crr_temps, t.getId_crr_temps());
            values.put(id_equ_temps, t.getId_equ_temps());
            values.put(id_typetour_temps, t.getId_typetour_temps());
            values.put(date_temps, t.getDate_temps());

            bdd.insert(TABLE, null, values);
        }
    }
}
