package com.cleanup.todoc.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;


import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProjectsTable(Project[] projects);

    @Query("SELECT * FROM project WHERE id = id")
    List<Project> getAllProjects();

}
