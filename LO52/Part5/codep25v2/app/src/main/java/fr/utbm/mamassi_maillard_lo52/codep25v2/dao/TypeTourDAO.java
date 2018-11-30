package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;

import static fr.utbm.mamassi_maillard_lo52.codep25v2.dao.DBHelper.*;

public class TypeTourDAO {

    public static final String TAG = "TypeTourDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            COLUMN_TypeTour_idTypeTour,
            COLUMN_TypeTour_titreTypeTour,
            COLUMN_TypeTour_ordreTypeTour,
            COLUMN_TypeTour_idTypeCourse,
    };

    public TypeTourDAO(Context context) {
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

    public TypeTour createTypeTour(String titre, int ordre, TypeCourse typeCourse) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TypeTour_titreTypeTour, titre);
        values.put(COLUMN_TypeTour_ordreTypeTour, ordre);
        values.put(COLUMN_TypeTour_idTypeCourse, typeCourse.getmId());
        long insertId = mDatabase.insert(TABLE_TypeTour, null, values);
        Cursor cursor = mDatabase.query(TABLE_TypeTour, mAllColumns,COLUMN_TypeTour_idTypeTour + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TypeTour newTypeTour = cursorToTypeTour(cursor);
        cursor.close();
        return newTypeTour;
    }

    public void deleteTypeTour(TypeTour typeTour) {
        long id = typeTour.getmId();



        System.out.println("the deleted TypeTour has the id: " + id);
        mDatabase.delete(TABLE_TypeTour, COLUMN_TypeTour_idTypeTour + " = " + id, null);
    }

    public TypeTour getTypeTourById(long id) {
        Cursor cursor = mDatabase.query(TABLE_TypeTour, mAllColumns, COLUMN_TypeTour_idTypeTour + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TypeTour typeTour = cursorToTypeTour(cursor);
        return typeTour;
    }

    public List<TypeTour> getTypeTourOfTypeCourse(long id) {
        List<TypeTour> listItems = new ArrayList<TypeTour>();

        Cursor cursor = mDatabase.query(TABLE_TypeTour, mAllColumns, COLUMN_TypeTour_idTypeCourse + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TypeTour typeTour = cursorToTypeTour(cursor);
            listItems.add(typeTour);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listItems;
    }

    protected TypeTour cursorToTypeTour(Cursor cursor) {
        TypeTour typeTour = new TypeTour();
        typeTour.setmId(cursor.getLong(0));
        typeTour.setmTitre(cursor.getString(1));
        typeTour.setmOrdre(cursor.getInt(2));

        long idTemp = cursor.getLong(3);
        TypeCourseDAO dao = new TypeCourseDAO(mContext);
        TypeCourse typeCourse = dao.getTypeCourseById(idTemp);
        if (typeCourse != null)
            typeTour.setmTypeCourse(typeCourse);

        return typeTour;
    }

    public int getLastOrderFromTypeCourseId(long id){
        Cursor cursor = mDatabase.rawQuery("select max(ordre) from "+TABLE_TypeTour+" where "+COLUMN_TypeTour_idTypeCourse+"="+id,null);
        cursor.moveToFirst();
        int res = -1;
        for(int i = 0 ; i < cursor.getCount() ; i++){
            res = cursor.getInt(0);
            cursor.moveToNext();
        }

        return res;
    }

}

