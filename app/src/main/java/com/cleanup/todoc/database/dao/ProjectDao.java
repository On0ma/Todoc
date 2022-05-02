package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM Project")
    List<Project> getAllProjects();

    @Query("SELECT * FROM Project WHERE id = :projectId")
    Project getProject(long projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProjects(List<Project> projects);
}
