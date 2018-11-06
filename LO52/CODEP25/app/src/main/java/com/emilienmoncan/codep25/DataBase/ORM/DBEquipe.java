package com.emilienmoncan.codep25.DataBase.ORM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emilienmoncan.codep25.DataBase.MySQLiteDatabase;
import com.emilienmoncan.codep25.Objects.Equipe;

import java.util.ArrayList;

public class DBEquipe {

    private static final String TABLE = "EQUIPE";
    private static final String id_equ = "id_equ";
    private static final int num_id_equ = 0;
    private static final String name_equ = "name_equ";
    private static final int num_name_equ = 1;
    private SQLiteDatabase bdd;
    private MySQLiteDatabase maBaseSQLite;

    public DBEquipe(Context context) {
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


    public ArrayList<Equipe> getAllEquipe() {
        ArrayList<Equipe> equs = new ArrayList<>();

        Cursor c = bdd.rawQuery("SELECT * " +
                " FROM " + TABLE + ";", null);

        if (c.getCount() == 0) return null;

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); ++i) {
            Equipe equ = new Equipe();
            equ.setId_equ(c.getInt(num_id_equ));
            equ.setName_equ(c.getString(num_name_equ));

            equs.add(equ);
            c.moveToNext();
        }
        return equs;
    }
}
