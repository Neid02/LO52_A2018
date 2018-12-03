package adury_csanchez.utbm.f1levier.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Team;

public class RaceDAO {
    public static final String TAG = "RaceDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_RACE_ID,
            DBHelper.COLUMN_RACE_NAME
    };

    public RaceDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Race createRace(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_RACE_NAME, name);

        long insertId = mDatabase.insert(DBHelper.TABLE_RACES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RACES, mAllColumns,DBHelper.COLUMN_RACE_ID + " = " + insertId,
                null, null,null, null);
        cursor.moveToFirst();
        Race newRace = cursorToRace(cursor);
        cursor.close();
        return newRace;
    }

    public void deleteRace(Race Race) {
        long id = Race.getId();
        // TODO delete all subscripton to this Race

        // TODO delete all laptimes associated to this Race

        System.out.println("the deleted Race has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_RACES, DBHelper.COLUMN_RACE_ID
                + " = " + id, null);
    }

    public List<Race> getAllRaces() {
        List<Race> listRaces = new ArrayList<Race>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_RACES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Race Race = cursorToRace(cursor);
                listRaces.add(Race);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listRaces;
    }

    public Race getRaceById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RACES, mAllColumns,
                DBHelper.COLUMN_RACE_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Race Race = cursorToRace(cursor);
        return Race;
    }

    protected Race cursorToRace(Cursor cursor) {
        Race Race = new Race();
        Race.setId(cursor.getLong(0));
        Race.setName(cursor.getString(1));
        return Race;
    }
}

