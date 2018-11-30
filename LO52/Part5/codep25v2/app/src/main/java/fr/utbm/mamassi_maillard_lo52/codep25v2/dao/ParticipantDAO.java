package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.ParticipantParEquipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;


public class ParticipantDAO {

    public static final String TAG = "ParticipantDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String mTable_Name = DBHelper.TABLE_Participant;
    private String[] mAllColumns = {
            DBHelper.COLUMN_Participant_idParticipant,
            DBHelper.COLUMN_Participant_nom,
            DBHelper.COLUMN_Participant_prenom,
            DBHelper.COLUMN_Participant_echelon,
            DBHelper.COLUMN_Participant_idStage,
    };

    public ParticipantDAO(Context context) {
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

    public Participant createParticipant(String nom, String prenom, int echelon, Stage stage) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_Participant_nom, nom);
        values.put(DBHelper.COLUMN_Participant_prenom, prenom);
        values.put(DBHelper.COLUMN_Participant_echelon, echelon);
        values.put(DBHelper.COLUMN_Participant_idStage, stage.getmId());
        long insertId = mDatabase.insert(mTable_Name, null, values);
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns,DBHelper.COLUMN_Participant_idParticipant + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Participant newParticipant = cursorToParticipant(cursor);
        cursor.close();
        return newParticipant;
    }

    public void deleteParticipant(Participant participant) {
        long id = participant.getmId();

        /*supprime les ParticipantParEquipe associé au participant*/
        ParticipantParEquipeDAO participantParEquipeDAO = new ParticipantParEquipeDAO(mContext);
        List<ParticipantParEquipe> listParticipantParEquipe = participantParEquipeDAO.getParticipantParEquipeOfParticipant(id);
        if (listParticipantParEquipe != null && !listParticipantParEquipe.isEmpty()) {
            for (ParticipantParEquipe e : listParticipantParEquipe) {
                participantParEquipeDAO.deleteParticipantParEquipe(e);
            }
        }

        System.out.println("the deleted Participant has the id: " + id);
        mDatabase.delete(mTable_Name, DBHelper.COLUMN_Participant_idParticipant + " = " + id, null);
    }

    public List<Participant> getParticipantOfStage(long id) {
        List<Participant> listItems = new ArrayList<Participant>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Participant_idStage + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Participant participant = cursorToParticipant(cursor);
                listItems.add(participant);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }


    public Participant getParticipantById(long id) {
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Participant_idParticipant + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Participant participant = cursorToParticipant(cursor);
        return participant;
    }

    protected Participant cursorToParticipant(Cursor cursor) {
        Participant participant = new Participant();
        participant.setmId(cursor.getLong(0));
        participant.setmNom(cursor.getString(1));
        participant.setmPrenom(cursor.getString(2));
        participant.setmEchelon(cursor.getInt(3));

        long idTemp = cursor.getLong(4);
        StageDAO stageDao = new StageDAO(mContext);
        Stage stage = stageDao.getStageById(idTemp);
        if (stage != null)
            participant.setmStage(stage);

        return participant;
    }
}
