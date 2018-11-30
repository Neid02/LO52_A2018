package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;

public class TypeCourseDAO {

    public static final String TAG = "TypeCourseDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DBHelper.COLUMN_TypeCourse_idTypeCourse,
            DBHelper.COLUMN_TypeCourse_titreTypeCourse,
    };

    public TypeCourseDAO(Context context) {
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

    public TypeCourse createTypeCourse(String titre) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TypeCourse_titreTypeCourse, titre);
        long insertId = mDatabase.insert(DBHelper.TABLE_TypeCourse, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TypeCourse, mAllColumns,DBHelper.COLUMN_TypeCourse_idTypeCourse + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TypeCourse newTypeCourse = cursorToTypeCourse(cursor);
        cursor.close();
        return newTypeCourse;
    }

    public void deleteTypeCourse(TypeCourse typeCourse) {
        long id = typeCourse.getmId();
        // delete all employees of this company
        TypeTourDAO typeTourDao = new TypeTourDAO(mContext);
        List<TypeTour> listTypeTour = typeTourDao.getTypeTourOfTypeCourse(id);
        if (listTypeTour != null && !listTypeTour.isEmpty()) {
            for (TypeTour e : listTypeTour) {
                typeTourDao.deleteTypeTour(e);
            }
        }

        System.out.println("the deleted TypeCourse has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_TypeCourse, DBHelper.COLUMN_TypeCourse_idTypeCourse + " = " + id, null);
    }

    public List<TypeCourse> getAllTypeCourse() {
        List<TypeCourse> listItems = new ArrayList<TypeCourse>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TypeCourse, mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TypeCourse typeCourse = cursorToTypeCourse(cursor);
                listItems.add(typeCourse);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }

    public TypeCourse getTypeCourseById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TypeCourse, mAllColumns, DBHelper.COLUMN_TypeCourse_idTypeCourse + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TypeCourse typeCourse = cursorToTypeCourse(cursor);
        return typeCourse;
    }

    protected TypeCourse cursorToTypeCourse(Cursor cursor) {
        TypeCourse typeCourse = new TypeCourse();
        typeCourse.setmId(cursor.getLong(0));
        typeCourse.setmTitre(cursor.getString(1));
        return typeCourse;
    }

}

