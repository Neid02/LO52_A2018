package fr.utbm.lo52.repository.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.utbm.lo52.domain.Category;


@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category WHERE Id = :Category")
    LiveData<List<Category>> getCategory(long Category);

    @Insert
    long insertCategory(Category category);

    @Update
    int updateCategory(Category category);

    @Query("DELETE FROM Category WHERE Id = :IdCategory")
    int deleteCategory(long IdCategory);
}


