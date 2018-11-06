package fr.utbm.lo52.repository;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import java.util.Date;
        import java.util.Optional;

        import fr.utbm.lo52.domain.Race;

        import static android.provider.BlockedNumberContract.BlockedNumbers.COLUMN_ID;

public class RaceDao extends BaseDao {

    public static final String TABLE_NAME = "Race";
    public static final String KEY = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + DATE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RaceDao(Context pContext) {
        super(pContext);
    }

    /**
     * @param r la course à ajouter à la base
     */
    public boolean create(Race r) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues contentValues= new ContentValues();
        contentValues.put(NAME, r.getName());
        contentValues.put(DATE, r.getName());
        long result= db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }


    public Cursor allRace() {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME,null);
        return result;
    }


    /**
     * @param id l'identifiant de la course à supprimer
     */
    public void delete(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        db.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param r la course modifié
     */
    public void update(Race r) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues value = new ContentValues();
        value.put(NAME, r.getName());
       // value.put(DATE, r.getDate());
        db.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(r.getId())});

    }

    /**
     * @param id l'identifiant de la course à récupérer
     */
    public Race findRaceById(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME + " where id =?",new String[]{String.valueOf(id)});
        Race race;
        if(result==null){
             race = new Race();
        }
        else {
            long idRace = result.getLong(0);
            String name = result.getString(1);
            String date = result.getString(2);
            Date dateS = new Date();
            //dateS=(Date)date;
             race = new Race(idRace, name, dateS);
        }
        return race;
    }
}

