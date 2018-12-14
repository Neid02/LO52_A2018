package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Course;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Equipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.ParticipantParEquipe;


public class EquipeDAO {

    public static final String TAG = "EquipeDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String mTable_Name = DBHelper.TABLE_Equipe;
    private String[] mAllColumns = {
            DBHelper.COLUMN_Equipe_idEquipe,
            DBHelper.COLUMN_Equipe_nomEquipe,
            DBHelper.COLUMN_Equipe_idCourse
    };

    public EquipeDAO(Context context) {
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

    public Equipe createEquipe(String nomEquipe, Course course) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_Equipe_nomEquipe, nomEquipe);
        values.put(DBHelper.COLUMN_Equipe_idCourse, course.getmId());
        long insertId = mDatabase.insert(mTable_Name, null, values);
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns,DBHelper.COLUMN_Equipe_idEquipe + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Equipe newEquipe = cursorToEquipe(cursor);
        cursor.close();
        return newEquipe;
    }

    public void deleteEquipe(Equipe equipe) {
        long id = equipe.getmId();

        /*supprime les ParticipantParEquipe associé à la course*/
        ParticipantParEquipeDAO participantParEquipeDAO = new ParticipantParEquipeDAO(mContext);
        List<ParticipantParEquipe> listParticipantParEquipe = participantParEquipeDAO.getParticipantParEquipeOfEquipe(id);
        if (listParticipantParEquipe != null && !listParticipantParEquipe.isEmpty()) {
            for (ParticipantParEquipe e : listParticipantParEquipe) {
                participantParEquipeDAO.deleteParticipantParEquipe(e);
            }
        }


        System.out.println("the deleted Equipe has the id: " + id);
        mDatabase.delete(mTable_Name, DBHelper.COLUMN_Equipe_idEquipe + " = " + id, null);
    }

    public List<Equipe> getEquipeOfCourse(long id) {
        List<Equipe> listItems = new ArrayList<Equipe>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Equipe_idCourse + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Equipe equipe = cursorToEquipe(cursor);
                listItems.add(equipe);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }


    public Equipe getEquipeById(long id) {
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Equipe_idEquipe + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Equipe equipe = cursorToEquipe(cursor);
        return equipe;
    }

    protected Equipe cursorToEquipe(Cursor cursor) {
        Equipe equipe = new Equipe();
        equipe.setmId(cursor.getLong(0));
        equipe.setmNom(cursor.getString(1));

        long idTemp = cursor.getLong(2);
        CourseDAO courseDao = new CourseDAO(mContext);
        Course course = courseDao.getCourseById(idTemp);
        if (course != null)
            equipe.setmCourse(course);

        return equipe;
    }
}
