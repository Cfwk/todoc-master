package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by Skiti on 22/10/2019
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTask(long projectId);

    @Insert
    long insertItem(Task item);

    @Update
    int updateItem(Task item);

    @Query("DELETE FROM Task" +
            " WHERE id = :itemId")
    int deleteItem(long itemId);
}
