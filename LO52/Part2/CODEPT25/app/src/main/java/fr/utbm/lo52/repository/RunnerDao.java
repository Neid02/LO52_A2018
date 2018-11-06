package fr.utbm.lo52.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import fr.utbm.lo52.domain.Race;
import fr.utbm.lo52.domain.Runner;

public class RunnerDao extends BaseDao {

    public static final String TABLE_NAME = "Runner";
    public static final String KEY = "id";
    public static final String KEY_LEVEL = "idLevel";
    public static final String LAST_NAME = "lastname";
    public static final String FIRST_NAME = "firstname";
    public static final String ECHELON = "echelon";
    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String CATEGORY_KEY = "id";


   public static final String TABLE_CREATE =
           "CREATE TABLE " + TABLE_NAME + " (" +
                   KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   KEY_LEVEL + " INTEGER NOT NULL , " +
                   LAST_NAME + " TEXT, " +
                   FIRST_NAME + " TEXT, " +
                   ECHELON + " INTEGER,"+
                   "FOREIGN KEY("+ KEY_LEVEL +") REFERENCES "+CATEGORY_TABLE_NAME+"("+CATEGORY_KEY+"));";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RunnerDao(Context pContext) {
        super(pContext);
    }

    /**
     * @param p la course à ajouter à la base
     */
    public boolean create(Runner p) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues contentValues= new ContentValues();
        contentValues.put(KEY_LEVEL, p.getIdLevel());
        contentValues.put(LAST_NAME, p.getLastName());
        contentValues.put(FIRST_NAME, p.getFirstName());
        contentValues.put(ECHELON, p.getEchelon());
        long result= db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }


    public Cursor allRunner() {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME,null);
        return result;
    }


    /**
     * @param id l'identifiant de la personne à supprimer
     */
    public void delete(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        db.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param p la personne modifié
     */
    public void update(Runner p) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues value = new ContentValues();
        value.put(KEY_LEVEL, p.getIdLevel());
        value.put(LAST_NAME, p.getLastName());
        value.put(FIRST_NAME, p.getFirstName());
        value.put(ECHELON, p.getEchelon());
        db.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(p.getId())});

    }

    /**
     * @param id l'identifiant de la personne à récupérer
     */
    public Runner findRaceById(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME + " where id =?",new String[]{String.valueOf(id)});
        Runner runner;
        if(result==null){
            runner = new Runner();
        }
        else {
            long idRunner = result.getLong(0);
            long idLevel = result.getLong(1);
            String lastname = result.getString(2);
            String firstname = result.getString(3);
            long echelon = result.getLong(4);
            runner = new Runner(idRunner, idLevel,lastname, firstname,echelon);
        }
        return runner;
    }
}

