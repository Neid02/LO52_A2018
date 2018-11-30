package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Equipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.ParticipantParEquipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Tour;


public class ParticipantParEquipeDAO {

    public static final String TAG = "ParticipantParEquipeDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String mTable_Name = DBHelper.TABLE_ParticipantParEquipe;
    private String[] mAllColumns = {
            DBHelper.COLUMN_ParticipantParEquipe_idPartEquipe,
            DBHelper.COLUMN_ParticipantParEquipe_ordrePassage,
            DBHelper.COLUMN_ParticipantParEquipe_groupe,
            DBHelper.COLUMN_ParticipantParEquipe_idParticipant,
            DBHelper.COLUMN_ParticipantParEquipe_idEquipe
    };

    public ParticipantParEquipeDAO(Context context) {
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

    public ParticipantParEquipe createParticipantParEquipe(int ordrePassage, String groupe, Participant participant, Equipe equipe) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ParticipantParEquipe_ordrePassage, ordrePassage);
        values.put(DBHelper.COLUMN_ParticipantParEquipe_groupe, groupe);
        values.put(DBHelper.COLUMN_ParticipantParEquipe_idParticipant, participant.getmId());
        values.put(DBHelper.COLUMN_ParticipantParEquipe_idEquipe, equipe.getmId());
        long insertId = mDatabase.insert(mTable_Name, null, values);
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns,DBHelper.COLUMN_ParticipantParEquipe_idPartEquipe + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        ParticipantParEquipe newParticipantParEquipe = cursorToParticipantParEquipe(cursor);
        cursor.close();
        return newParticipantParEquipe;
    }

    public void deleteParticipantParEquipe(ParticipantParEquipe participantParEquipe) {
        long id = participantParEquipe.getmId();

        //supprime les tours associee au participantParEquipe
        TourDAO tourDAO = new TourDAO(mContext);
        List<Tour> listTour = tourDAO.getTourOfParticipantParEquipe(id);
        if (listTour != null && !listTour.isEmpty()) {
            for (Tour e : listTour) {
                tourDAO.deleteTour(e);
            }
        }

        System.out.println("the deleted ParticipantParEquipe has the id: " + id);
        mDatabase.delete(mTable_Name, DBHelper.COLUMN_ParticipantParEquipe_idPartEquipe + " = " + id, null);
    }

    public List<ParticipantParEquipe> getParticipantParEquipeOfEquipe(long id) {
        List<ParticipantParEquipe> listItems = new ArrayList<ParticipantParEquipe>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_ParticipantParEquipe_idEquipe + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ParticipantParEquipe participantParEquipe = cursorToParticipantParEquipe(cursor);
                listItems.add(participantParEquipe);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return listItems;
    }

    public List<ParticipantParEquipe> getParticipantParEquipeOfParticipant(long id) {
        List<ParticipantParEquipe> listItems = new ArrayList<ParticipantParEquipe>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_ParticipantParEquipe_idParticipant + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ParticipantParEquipe participantParEquipe = cursorToParticipantParEquipe(cursor);
                listItems.add(participantParEquipe);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return listItems;
    }


    public ParticipantParEquipe getParticipantParEquipeById(long id) {
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_ParticipantParEquipe_idPartEquipe + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        ParticipantParEquipe participantParEquipe = cursorToParticipantParEquipe(cursor);
        return participantParEquipe;
    }

    protected ParticipantParEquipe cursorToParticipantParEquipe(Cursor cursor) {
        ParticipantParEquipe participantParEquipe = new ParticipantParEquipe();
        participantParEquipe.setmId(cursor.getLong(0));
        participantParEquipe.setmOrdre(cursor.getInt(1));
        participantParEquipe.setmGroupe(cursor.getString(2));

        long idTemp = cursor.getLong(3);
        ParticipantDAO participantDAO = new ParticipantDAO(mContext);
        Participant participant = participantDAO.getParticipantById(idTemp);
        if (participant != null)
            participantParEquipe.setmParticipant(participant);

        idTemp = cursor.getLong(4);
        EquipeDAO equipeDAO = new EquipeDAO(mContext);
        Equipe equipe = equipeDAO.getEquipeById(idTemp);
        if (equipe != null)
            participantParEquipe.setmEquipe(equipe);

        return participantParEquipe;
    }
}

