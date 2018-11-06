package fr.utbm.lo52.repository;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

public class BaseDao {

    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "lo52codep25.db"
            ;

    protected SQLiteDatabase mDb = null;
    protected DataBaseHandler mHandler = null;

    public BaseDao(Context pContext) {
        this.mHandler = new DataBaseHandler(pContext, NOM, null, VERSION);
    }
    public BaseDao(){};

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}

