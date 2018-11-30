package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.ParticipantParEquipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Tour;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;


public class TourDAO {

    public static final String TAG = "TourDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String mTable_Name = DBHelper.TABLE_Tour;
    private String[] mAllColumns = {
            DBHelper.COLUMN_Tour_idTour,
            DBHelper.COLUMN_Tour_temps,
            DBHelper.COLUMN_Tour_idPartParEquipe,
            DBHelper.COLUMN_Tour_idTypeTour
    };

    public TourDAO(Context context) {
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

    public Tour createTour(String temps, ParticipantParEquipe participantParEquipe, TypeTour typeTour) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_Tour_temps, temps);
        values.put(DBHelper.COLUMN_Tour_idPartParEquipe, participantParEquipe.getmId());
        values.put(DBHelper.COLUMN_Tour_idTypeTour, typeTour.getmId());
        long insertId = mDatabase.insert(mTable_Name, null, values);
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns,DBHelper.COLUMN_Tour_idTour + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Tour newTour = cursorToTour(cursor);
        cursor.close();
        return newTour;
    }

    public void deleteTour(Tour tour) {
        long id = tour.getmId();

        System.out.println("the deleted Tour has the id: " + id);
        mDatabase.delete(mTable_Name, DBHelper.COLUMN_Tour_idTour + " = " + id, null);
    }

    public List<Tour> getTourOfParticipantParEquipe(long id) {
        List<Tour> listItems = new ArrayList<Tour>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Tour_idPartParEquipe + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Tour tour = cursorToTour(cursor);
                listItems.add(tour);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }


    public Tour getTourById(long id) {
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Tour_idTour + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Tour tour = cursorToTour(cursor);
        return tour;
    }

    protected Tour cursorToTour(Cursor cursor) {
        Tour tour = new Tour();
        tour.setmId(cursor.getLong(0));
        tour.setMtemps(cursor.getString(1));

        long idTemp = cursor.getLong(2);
        ParticipantParEquipeDAO participantParEquipeDAO = new ParticipantParEquipeDAO(mContext);
        ParticipantParEquipe participantParEquipe = participantParEquipeDAO.getParticipantParEquipeById(idTemp);
        if (participantParEquipe != null)
            tour.setmParticipantParEquipe(participantParEquipe);

        idTemp = cursor.getLong(3);
        TypeTourDAO typeTourDao = new TypeTourDAO(mContext);
        TypeTour typeTour = typeTourDao.getTypeTourById(idTemp);
        if (typeTour != null)
            tour.setmTypeTour(typeTour);

        return tour;
    }
}
