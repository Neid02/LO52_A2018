package com.silentpangolin.codep25.DataBase.ORM;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.silentpangolin.codep25.DataBase.MySQLiteDatabase;

public class DBTypeTour {

    private static final String TABLE = "TYPETOUR";
    private static final String id_typetour = "id_typetour";
    private static final int num_id_typetour = 0;
    private static final String initials_typetour = "initials_typetour";
    private static final int num_initials_typetour = 1;
    private static final String name_typetour = "name_typetour";
    private static final int num_name_typetour = 2;
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

    public int getIDTypeTourWithNbTours(int nbTours){
        /**
         *  1 = sprint - sp
         *  2 = fractionné - fr
         *  3 = pit-stop - ps
         *  4 = sprint - sp
         *  5 = facxtionné - fr
         *  default or -1 = Général - gn
         */
        Cursor c;
        switch(nbTours){
            case 1 :
            case 4 :
                c = bdd.rawQuery("SELECT " + id_typetour + " FROM " + TABLE + " WHERE " + initials_typetour + " = 'sp';", null);
                break;
            case 2 :
            case 5 :
                c = bdd.rawQuery("SELECT " + id_typetour + " FROM " + TABLE + " WHERE " + initials_typetour + " = 'fr';", null);
                break;
            case 3 :
                c = bdd.rawQuery("SELECT " + id_typetour + " FROM " + TABLE + " WHERE " + initials_typetour + " = 'ps';", null);
                break;
            case -1 :
                c = bdd.rawQuery("SELECT " + id_typetour + " FROM " + TABLE + " WHERE " + initials_typetour + " = 'gn';", null);
                break;
            default:
                c = bdd.rawQuery("SELECT " + id_typetour + " FROM " + TABLE + " WHERE " + initials_typetour + " = 'gn';", null);
                break;
        }

        if (c.getCount() == 0) return -1;

        c.moveToFirst();
        int ID = c.getInt(0);
        c.close();

        return ID;
    }

}
