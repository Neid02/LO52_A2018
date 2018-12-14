package com.silentpangolin.codep25.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.silentpangolin.codep25.Objects.Coureur;

import java.util.ArrayList;

public class MySQLiteDatabase  extends SQLiteOpenHelper {

    private static int VERSION_DB = 2;
    private static String NOM_DB = "codep25.db";
    private Context context;

    public MySQLiteDatabase(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, getNomDb(), factory, getVersionBdd());
        this.context = context;
    }

    public static int getVersionBdd() {
        return VERSION_DB;
    }

    public static String getNomDb() { return NOM_DB;  }

    public static final String CREATE_TABLE_TYPETOUR =
            "CREATE TABLE "
            + "TYPETOUR ("
            + "id_typetour INTEGER PRIMARY KEY, "
            + "initials_typetour TEXT NOT NULL, "
            + "name_typetour TEXT NOT NULL"
            + ");";

    public static final String CREATE_TABLE_EQUIPE =
            "CREATE TABLE "
            + "EQUIPE ("
            + "id_equ INTEGER PRIMARY KEY, "
            + "name_equ TEXT NOT NULL"
            + ");";

    public static final String CREATE_TABLE_COUREUR =
            "CREATE TABLE "
            + "COUREUR ("
            + "id_crr INTEGER PRIMARY KEY, "
            + "nom_crr TEXT NOT NULL, "
            + "prenom_crr TEXT NOT NULL, "
            + "echelon_crr INTEGER NOT NULL, "
            + "ordrepassage_crr INTEGER NOT NULL, "
            + "id_equ_crr INTEGER NOT NULL, "
            + "FOREIGN KEY(id_equ_crr) REFERENCES EQUIPE(id_equ));";

    public static final String CREATE_TABLE_TEMPS =
            "CREATE TABLE "
            + "TEMPS ("
            + "id_temps INTEGER PRIMARY KEY, "
            + "duree_temps INTEGER NOT NULL, "
            + "id_crr_temps INTEGER NOT NULL, "
            + "id_equ_temps INTEGER NOT NULL, "
            + "id_typetour_temps INTEGER NOT NULL, "
            + "date_temps INTEGER NOT NULL, "
            + "FOREIGN KEY(id_crr_temps) REFERENCES COUREUR(id_crr), "
            + "FOREIGN KEY(id_typetour_temps) REFERENCES TYPETOUR(id_typetour));";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TYPETOUR);
        db.execSQL(CREATE_TABLE_EQUIPE);
        db.execSQL(CREATE_TABLE_COUREUR);
        db.execSQL(CREATE_TABLE_TEMPS);

        try{
            String[] nameTeams = {"Zenithar","Kynareth","Asari","Krogan","Galarien","Volus","Geth","Turien","Mara","Orsimer","Nordique","Julianos","Dibella","Argonien","Dunmer","Elcor","Dragon","Falmer","Dovakhiin","Bosmer","Septim","Humain","Prothéen","Butarien","Hanari","Altmer","Rougegarde","Bréton","Promo 16 !","Prince Daedra","Khajiit","Maormer","Akatosh","Stendarr","Thorien","Impériaux","Dwemer","Rachni","Veilleurs","Moissonneur","Quarien","Arkay"};
            ArrayList<Coureur> all = createList();
            int gap = 1, count, average = 0;
            ArrayList<String> insertTeams = new ArrayList<>();

            for (Coureur c : all) average += c.getEchelon_crr();
            average /= all.size();
            average *= 3;
            boolean[] isInTeam = new boolean[all.size()];
            boolean valid = true;

            while (valid) {
                count = 0;
                insertTeams = new ArrayList<>();
                for (int i = 0; i < all.size(); ++i) {
                    isInTeam[i] = false;
                }
                for (Coureur c : new ArrayList<>(all)) {
                    for (Coureur b : new ArrayList<>(all)) {
                        for (Coureur a : new ArrayList<>(all)) {
                            if ((c.getId_crr() != b.getId_crr()) && (b.getId_crr() != a.getId_crr()) && (c.getId_crr() != a.getId_crr())
                                    && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)]) && !(isInTeam[all.indexOf(a)])) {
                                if (((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) < (average + gap))
                                        && ((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) > (average - gap))) {
                                    isInTeam[all.indexOf(c)] = true;
                                    isInTeam[all.indexOf(b)] = true;
                                    isInTeam[all.indexOf(a)] = true;
                                    c.setId_equ_crr(count);
                                    b.setId_equ_crr(count);
                                    a.setId_equ_crr(count);
                                    c.setOrdrepassage_crr(1);
                                    b.setOrdrepassage_crr(2);
                                    a.setOrdrepassage_crr(3);
                                    insertTeams.add("INSERT INTO EQUIPE(id_equ, name_equ) VALUES (" + count + ", '" + nameTeams[count] + "');");
                                    ++count;
                                }
                            }
                        }
                        if ((c.getId_crr() != b.getId_crr()) && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)])) {
                            if (((c.getEchelon_crr() + b.getEchelon_crr()) < (average + gap)) && ((c.getEchelon_crr() + b.getEchelon_crr()) > (average - gap))) {
                                isInTeam[all.indexOf(c)] = true;
                                isInTeam[all.indexOf(b)] = true;
                                c.setId_equ_crr(count);
                                b.setId_equ_crr(count);
                                c.setOrdrepassage_crr(1);
                                b.setOrdrepassage_crr(2);
                                insertTeams.add("INSERT INTO EQUIPE(id_equ, name_equ) VALUES (" + count + ", '" + nameTeams[count] + "');");
                                ++count;
                            }
                        }
                    }
                }
                valid = false;
                for (int i = 0; i < all.size(); ++i) {
                    if (!(isInTeam[i])) {
                        valid = true;
                    }
                }
                ++gap;
            }
            for (String s : insertTeams)
                db.execSQL(s);
            for(Coureur c : all)
                db.execSQL("INSERT INTO COUREUR(nom_crr, prenom_crr, echelon_crr, ordrepassage_crr, id_equ_crr) VALUES ('" +
                        c.getNom_crr() + "', '" +
                        c.getPrenom_crr() + "', " +
                        c.getEchelon_crr() + ", " +
                        c.getOrdrepassage_crr() + ", " +
                        c.getId_equ_crr() + ");" );

            db.execSQL("INSERT INTO TYPETOUR(initials_typetour, name_typetour) VALUES ('sp', 'Sprint')");
            db.execSQL("INSERT INTO TYPETOUR(initials_typetour, name_typetour) VALUES ('fr', 'Fractionné')");
            db.execSQL("INSERT INTO TYPETOUR(initials_typetour, name_typetour) VALUES ('ps', 'Pit-Stop')");
            db.execSQL("INSERT INTO TYPETOUR(initials_typetour, name_typetour) VALUES ('gn', 'Général')");

        }catch(Exception e){
            e.printStackTrace();
            Log.e("MySQLite", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public static ArrayList<Coureur> createList() {
        ArrayList<Coureur> crrs = new ArrayList<>();

        crrs.add(new Coureur(1, "Personne", "1", 0, 0, 0));
        crrs.add(new Coureur(2, "Personne", "2", 1, 0, 0));
        crrs.add(new Coureur(3, "Personne", "3", 2, 0, 0));
        crrs.add(new Coureur(4, "Personne", "4", 6, 0, 0));
        crrs.add(new Coureur(5, "Personne", "5", 9, 0, 0));
        crrs.add(new Coureur(6, "Personne", "6", 11, 0, 0));
        crrs.add(new Coureur(7, "Personne", "7", 12, 0, 0));
        crrs.add(new Coureur(8, "Personne", "8", 12, 0, 0));
        crrs.add(new Coureur(9, "Personne", "9", 19, 0, 0));
        crrs.add(new Coureur(10, "Personne", "10", 20, 0, 0));
        crrs.add(new Coureur(11, "Personne", "11", 22, 0, 0));
        crrs.add(new Coureur(12, "Personne", "12", 22, 0, 0));
        crrs.add(new Coureur(13, "Personne", "13", 23, 0, 0));
        crrs.add(new Coureur(14, "Personne", "14", 23, 0, 0));
        crrs.add(new Coureur(15, "Personne", "15", 25, 0, 0));
        crrs.add(new Coureur(16, "Personne", "16", 30, 0, 0));
        crrs.add(new Coureur(17, "Personne", "17", 31, 0, 0));
        crrs.add(new Coureur(18, "Personne", "18", 32, 0, 0));
        crrs.add(new Coureur(19, "Personne", "19", 33, 0, 0));
        crrs.add(new Coureur(20, "Personne", "20", 34, 0, 0));
        crrs.add(new Coureur(21, "Personne", "21", 34, 0, 0));
        crrs.add(new Coureur(22, "Personne", "22", 48, 0, 0));
        crrs.add(new Coureur(23, "Personne", "23", 54, 0, 0));
        crrs.add(new Coureur(24, "Personne", "24", 56, 0, 0));
        crrs.add(new Coureur(25, "Personne", "25", 65, 0, 0));
        crrs.add(new Coureur(26, "Personne", "26", 75, 0, 0));
        crrs.add(new Coureur(27, "Personne", "27", 76, 0, 0));
        crrs.add(new Coureur(28, "Personne", "28", 87, 0, 0));
        crrs.add(new Coureur(29, "Personne", "29", 87, 0, 0));
        crrs.add(new Coureur(30, "Personne", "30", 89, 0, 0));
        crrs.add(new Coureur(31, "Personne", "31", 90, 0, 0));
        crrs.add(new Coureur(32, "Personne", "32", 100, 0, 0));

        Log.e("MySQLite", "List created");

        return crrs;
    }
}
