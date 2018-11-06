package fr.utbm.lo52.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import fr.utbm.lo52.domain.Category;
import fr.utbm.lo52.domain.Race;


public class CategoryDao extends BaseDao {

    public static final String TABLE_NAME = "Category";
    public static final String KEY = "id";
    public static final String LEVEL = "level";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LEVEL + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public CategoryDao(Context pContext) {
        super(pContext);
    }

    /**
     * @param c la categorie à ajouter à la base
     */
    public boolean create(Category c) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues contentValues= new ContentValues();
        contentValues.put(LEVEL, c.getLevel());
        long result= db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }


    public Cursor allCategory() {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME,null);
        return result;
    }


    /**
     * @param id l'identifiant de la categorie à supprimer
     */
    public void delete(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        db.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
    }

    /**
     * @param c la categorie modifié
     */
    public void update(Category c) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues value = new ContentValues();
        value.put(LEVEL, c.getLevel());
        db.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(c.getId())});
    }

    /**
     * @param id l'identifiant de la category à récupérer
     */
    public Category findCategoryById(long id) {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME + " where id =?",new String[]{String.valueOf(id)});
        Category category;
        if(result==null){
            category = new Category();
        }
        else {
            long idCategory = result.getLong(0);
            String level = result.getString(1);
            category = new Category(idCategory, level);
        }
        return category;

    }
}


