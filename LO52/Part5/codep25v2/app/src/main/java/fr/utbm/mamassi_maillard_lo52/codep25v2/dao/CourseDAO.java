package fr.utbm.mamassi_maillard_lo52.codep25v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Course;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Equipe;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Stage;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeTour;



public class CourseDAO {

    public static final String TAG = "CourseDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String mTable_Name = DBHelper.TABLE_Course;
    private String[] mAllColumns = {
            DBHelper.COLUMN_Course_idCourse,
            DBHelper.COLUMN_Course_titre,
            DBHelper.COLUMN_Course_date,
            DBHelper.COLUMN_Course_idStage,
            DBHelper.COLUMN_Course_idTypeCourse
    };

    public CourseDAO(Context context) {
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

    public Course createCourse(String titre, String date, Stage stage, TypeCourse typeCourse) {
        ContentValues values = new ContentValues();
        //titre
        values.put(DBHelper.COLUMN_Course_titre, titre);
        //date
        values.put(DBHelper.COLUMN_Course_date, date);
        //stageid
        values.put(DBHelper.COLUMN_Course_idStage, stage.getmId());
        //typeCourseid
        values.put(DBHelper.COLUMN_Course_idTypeCourse, typeCourse.getmId());
        long insertId = mDatabase.insert(mTable_Name, null, values);
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns,DBHelper.COLUMN_Course_idCourse + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Course newCourse = cursorToCourse(cursor);
        cursor.close();
        return newCourse;
    }

    public void deleteCourse(Course course) {
        long id = course.getmId();

        //supprime les equipes associee a la course
        EquipeDAO equipeDAO = new EquipeDAO(mContext);
        List<Equipe> listEquipe = equipeDAO.getEquipeOfCourse(id);
        if (listEquipe != null && !listEquipe.isEmpty()) {
            for (Equipe e : listEquipe) {
                equipeDAO.deleteEquipe(e);
            }
        }

        System.out.println("the deleted Course has the id: " + id);
        mDatabase.delete(mTable_Name, DBHelper.COLUMN_Course_idCourse + " = " + id, null);
    }

    public List<Course> getCourseOfStage(long id) {
        List<Course> listItems = new ArrayList<Course>();

        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Course_idStage + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course course = cursorToCourse(cursor);
                listItems.add(course);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listItems;
    }


    public Course getCourseById(long id) {
        Cursor cursor = mDatabase.query(mTable_Name, mAllColumns, DBHelper.COLUMN_Course_idCourse + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Course course = cursorToCourse(cursor);
        return course;
    }

    protected Course cursorToCourse(Cursor cursor) {
        Course course = new Course();
        course.setmId(cursor.getLong(0));
        course.setmTitre(cursor.getString(1));
        course.setmDate(cursor.getString(2));

        long idTemp = cursor.getLong(3);
        StageDAO stageDao = new StageDAO(mContext);
        Stage stage = stageDao.getStageById(idTemp);
        if (stage != null)
            course.setmStage(stage);

        idTemp = cursor.getLong(4);
        TypeCourseDAO typeCourseDao = new TypeCourseDAO(mContext);
        TypeCourse typeCourse = typeCourseDao.getTypeCourseById(idTemp);
        if (typeCourse != null)
            course.setmTypeCourse(typeCourse);

        return course;
    }
}
