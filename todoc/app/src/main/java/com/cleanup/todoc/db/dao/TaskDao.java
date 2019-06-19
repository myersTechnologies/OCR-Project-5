package com.cleanup.todoc.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task WHERE task_project_id = task_project_id")
    List<Task> getTasks();

    @Insert
    void insetTask(Task task);

    @Query("DELETE FROM Task WHERE task_id = :taskId")
    void deleteTask(long taskId);

}
