package com.silentpangolin.codep25.DataBase.ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.silentpangolin.codep25.DataBase.MySQLiteDatabase;

public class DBTypeTour {

    private static final String TABLE = "TYPETOUR";
    private static final String id_typetour = "id_typetour";
    private static final int num_id_typetour = 0;
    private static final String name_typetour = "name_typetour";
    private static final int num_name_typetour = 1;
    private SQLiteDatabase bdd;
    private MySQLiteDatabase maBaseSQLite;

    public DBTypeTour(Context context) {
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
