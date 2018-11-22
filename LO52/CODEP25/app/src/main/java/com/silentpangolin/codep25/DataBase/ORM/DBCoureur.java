package com.silentpangolin.codep25.DataBase.ORM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.silentpangolin.codep25.DataBase.MySQLiteDatabase;
import com.silentpangolin.codep25.Objects.Coureur;

import java.util.ArrayList;

public class DBCoureur {

    private static final String TABLE = "COUREUR";
    private static final String id_crr = "id_crr";
    private static final int num_id_crr= 0;
    private static final String nom_crr = "nom_crr";
    private static final int num_nom_crr = 1;
    private static final String prenom_crr = "prenom_crr";
    private static final int num_prenom_crr = 2;
    private static final String echelon_crr = "echelon_crr";
    private static final int num_echelon_crr = 3;
    private static final String ordrepassage_crr = "ordrepassage_crr";
    private static final int num_ordrepassage_crr = 4;
    private static final String id_equ_crr = "id_equ_crr";
    private static final int num_id_equ_crr = 5;
    private SQLiteDatabase bdd;
    private MySQLiteDatabase maBaseSQLite;

    public DBCoureur(Context context) {
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

    /**
     * Recupere tous les coureurs presents dans la base de donnee
     * @return ArrayList<Coureur>
     */
    public ArrayList<Coureur> getAllCoureur() {
        ArrayList<Coureur> crrs = new ArrayList<>();

        Cursor c = bdd.rawQuery("SELECT * " +
                " FROM " + TABLE + ";", null);

        if (c.getCount() == 0) return null;

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); ++i) {
            Coureur crr = new Coureur();
            crr.setId_crr(c.getInt(num_id_crr));
            crr.setNom_crr(c.getString(num_nom_crr));
            crr.setPrenom_crr(c.getString(num_prenom_crr));
            crr.setEchelon_crr(c.getInt(num_echelon_crr));
            crr.setOrdrepassage_crr(c.getInt(num_ordrepassage_crr));
            crr.setId_equ_crr(c.getInt(num_id_equ_crr));
            crrs.add(crr);

            c.moveToNext();
        }
        return crrs;
    }

    public ArrayList<Coureur> getAllCoureurWithIDTeam(int id) {
        ArrayList<Coureur> crrs = new ArrayList<>();

        Cursor c = bdd.rawQuery("SELECT * " +
                " FROM " + TABLE +
                " WHERE " + id_equ_crr + " = " + id +
                " ORDER BY " + ordrepassage_crr + ";", null);

        if (c.getCount() == 0) return null;

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); ++i) {
            Coureur crr = new Coureur();
            crr.setId_crr(c.getInt(num_id_crr));
            crr.setNom_crr(c.getString(num_nom_crr));
            crr.setPrenom_crr(c.getString(num_prenom_crr));
            crr.setEchelon_crr(c.getInt(num_echelon_crr));
            crr.setOrdrepassage_crr(c.getInt(num_ordrepassage_crr));
            crr.setId_equ_crr(c.getInt(num_id_equ_crr));
            crrs.add(crr);

            c.moveToNext();
        }
        return crrs;
    }

    public int getNumMaxCoureurByTeam(){
        Cursor c = bdd.rawQuery(
                "SELECT MAX(count) " +
                "FROM (" +
                    "SELECT COUNT(*) as count " +
                    " FROM " + TABLE +
                    " GROUP BY " + id_equ_crr +
                ");", null);

        if (c.getCount() == 0) return -1;

        c.moveToFirst();
        int temp = c.getInt(0);
        c.close();

        return temp;

    }

    /*public long insertQuotationPR(QuotationPR quopr) {
        ContentValues values = new ContentValues();
        values.put(QUOPR_QUOID, quopr.getQuoPR_QuoID());
        values.put(QUOPR_PRID, quopr.getQuoPR_PRID());
        values.put(QUOPR_QTE, quopr.getQuoPR_Qte());
        values.put(QUOPR_BUSID, quopr.getQuoPR_BusID());

        return bdd.insert(TABLE, null, values);
    }*/

    /**
     * Mise à jour de tous les coureurs
     *
     * @param crrs
     * @return int
     */
    public void updateAllCoureur(ArrayList<Coureur> crrs) {
        for(Coureur c : crrs){
            ContentValues values = new ContentValues();
            values.put(id_crr, c.getId_crr());
            values.put(nom_crr, c.getNom_crr());
            values.put(prenom_crr, c.getPrenom_crr());
            values.put(echelon_crr, c.getEchelon_crr());
            values.put(ordrepassage_crr, c.getOrdrepassage_crr());
            values.put(id_equ_crr, c.getId_equ_crr());
            bdd.update(TABLE, values, id_crr + " = " + c.getId_crr(), null);
        }
    }
}
