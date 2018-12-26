package fr.dfl.f1_levier;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

public class F1_LevierApp extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"course-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
