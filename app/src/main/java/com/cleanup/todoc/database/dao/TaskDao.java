package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    List<Task> getAllTasks();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task getTask(long id);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
}
