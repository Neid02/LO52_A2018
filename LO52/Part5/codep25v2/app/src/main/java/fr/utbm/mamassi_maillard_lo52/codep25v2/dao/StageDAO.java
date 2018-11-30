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
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;

public class StageDAO {

    public static final String TAG = "StageDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DBHelper.COLUMN_Stage_idStage,
            DBHelper.COLUMN_Stage_Titre,
    };

    public StageDAO(Context context) {
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

    public Stage createStage(String titre) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_Stage_Titre, titre);
        long insertId = mDatabase.insert(DBHelper.TABLE_Stage, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_Stage, mAllColumns,DBHelper.COLUMN_Stage_idStage + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Stage newTypeCourse = cursorToStage(cursor);
        cursor.close();
        return newTypeCourse;
    }

    public void deleteStage(Stage stage) {
        long id = stage.getmId();

        /*supprime les participants associé au stage*/
        ParticipantDAO participantDAO = new ParticipantDAO(mContext);
        List<Participant> listParticipant = participantDAO.getParticipantOfStage(id);
        if (listParticipant != null && !listParticipant.isEmpty()) {
            for (Participant e : listParticipant) {
                participantDAO.deleteParticipant(e);
            }
        }
        //supprime les courses associé au stage
        CourseDAO courseDAO = new CourseDAO(mContext);
        List<Course> listCourse = courseDAO.getCourseOfStage(id);
        if (listCourse != null && !listCourse.isEmpty()) {
            for (Course e : listCourse) {
                courseDAO.deleteCourse(e);
            }
        }

        System.out.println("the deleted Stage has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_Stage, DBHelper.COLUMN_Stage_idStage + " = " + id, null);
    }

    public List<Stage> getAllStage() {
        List<Stage> listItems = new ArrayList<Stage>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_Stage, mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Stage stage = cursorToStage(cursor);
                listItems.add(stage);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }

    public Stage getStageById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_Stage, mAllColumns, DBHelper.COLUMN_Stage_idStage + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Stage stage = cursorToStage(cursor);
        return stage;
    }

    protected Stage cursorToStage(Cursor cursor) {
        Stage stage = new Stage();
        stage.setmId(cursor.getLong(0));
        stage.setmTitre(cursor.getString(1));
        return stage;
    }
}
