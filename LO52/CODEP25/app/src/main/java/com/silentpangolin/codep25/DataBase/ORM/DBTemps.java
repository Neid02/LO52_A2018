package com.silentpangolin.codep25.DataBase.ORM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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

    public ArrayList<Temps> getAllTemps(){
        ArrayList<Temps> tps = new ArrayList<>();

        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE + ";", null);

        if(c.getCount() == 0) return null;

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); ++i){
            Temps t = new Temps();
            t.setId_temps(c.getInt(num_id_temps));
            t.setDuree_temps(c.getLong(num_duree_temps));
            t.setId_crr_temps(c.getInt(num_id_crr_temps));
            t.setId_equ_temps(c.getInt(num_id_equ_temps));
            t.setId_typetour_temps(c.getInt(num_id_typetour_temps));
            t.setDate_temps(c.getLong(num_date_temps));

            tps.add(t);

            c.moveToNext();
        }

        return tps;
    }

    public ArrayList<Temps> getAllTempsWithIDCoureurAndIDType(int ID, int Type){
        ArrayList<Temps> tps = new ArrayList<>();

        Cursor c = bdd.rawQuery(
                "SELECT *" +
                    " FROM " + TABLE +
                    " WHERE " + id_crr_temps + " = " + ID +
                    " AND " + id_typetour_temps + " = " + Type +
                    " ORDER BY " + duree_temps + " ASC;", null);

        if(c.getCount() == 0) return null;

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); ++i){
            Temps t = new Temps();
            t.setId_temps(c.getInt(num_id_temps));
            t.setDuree_temps(c.getLong(num_duree_temps));
            t.setId_crr_temps(c.getInt(num_id_crr_temps));
            t.setId_equ_temps(c.getInt(num_id_equ_temps));
            t.setId_typetour_temps(c.getInt(num_id_typetour_temps));
            t.setDate_temps(c.getLong(num_date_temps));

            tps.add(t);

            c.moveToNext();
        }

        return tps;
    }

    public ArrayList<Temps> getAllTempsWithIDType(int ID){
        ArrayList<Temps> tps = new ArrayList<>();

        Cursor c = bdd.rawQuery(
                "SELECT *" +
                        " FROM " + TABLE +
                        " WHERE " + id_typetour_temps + " = " + ID +
                        " ORDER BY " + duree_temps + " ASC;", null);

        if(c.getCount() == 0) return null;

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); ++i){
            Temps t = new Temps();
            t.setId_temps(c.getInt(num_id_temps));
            t.setDuree_temps(c.getLong(num_duree_temps));
            t.setId_crr_temps(c.getInt(num_id_crr_temps));
            t.setId_equ_temps(c.getInt(num_id_equ_temps));
            t.setId_typetour_temps(c.getInt(num_id_typetour_temps));
            t.setDate_temps(c.getLong(num_date_temps));

            tps.add(t);

            c.moveToNext();
        }

        return tps;
    }

    public ArrayList<Temps> getAVGTempsForTeamWithIDType(int ID){
        ArrayList<Temps> tps = new ArrayList<>();

        Cursor c = bdd.rawQuery(
                "SELECT AVG(" + duree_temps + ") AS moy, " + id_equ_temps +
                        " FROM " + TABLE +
                        " WHERE " + id_typetour_temps + " = " + ID +
                        " GROUP BY " + id_equ_temps +
                        " ORDER BY moy ASC;", null);

        if(c.getCount() == 0) return null;

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); ++i){
            Temps t = new Temps();
            t.setDuree_temps(c.getLong(0));
            t.setId_equ_temps(c.getInt(1));

            tps.add(t);

            c.moveToNext();
        }

        return tps;
    }

    public Temps getAVGTempsForPlayerWithIDType(int ID, int IDPlayer){
        Cursor c = bdd.rawQuery(
                "SELECT AVG(" + duree_temps + "), " + id_crr_temps +
                        " FROM " + TABLE +
                        " WHERE " + id_typetour_temps + " = " + ID +
                        " GROUP BY " + id_crr_temps +
                        " HAVING " + id_crr_temps + " = " + IDPlayer +
                        ";", null);

        if(c.getCount() == 0) return null;

        c.moveToFirst();
        Temps t = new Temps();
        t.setDuree_temps(c.getLong(0));
        t.setId_crr_temps(c.getInt(1));

        c.moveToNext();

        return t;
    }




    public void deleteIDTeam(){
        bdd.execSQL("UPDATE " + TABLE + " SET " + id_equ_temps + " = -1;");
    }

    public int removeTempsWithIDCoureur(int ID) {
        return bdd.delete(TABLE, id_crr_temps + " = " + ID, null);
    }
}
