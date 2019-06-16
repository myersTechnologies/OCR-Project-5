package com.cleanup.todoc.db.dao;

import android.app.Instrumentation;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    SaveTaskDatabase database;
    MainActivity main;

    private static long TASK_ID = 1;
    private static Task TASK_DEMO = new Task(TASK_ID, 1L, "Laver les vitres", 24/10/1991);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initDb(){
        main = mActivityTestRule.getActivity();
        this.database = Room.inMemoryDatabaseBuilder(main,
                SaveTaskDatabase.class).allowMainThreadQueries().build();
        database.projectDao().createProject(Project.getAllProjects());
    }

    @After
    public void closeDb(){
        database.close();
    }

    @Test
    public void insertAndGetTask(){
        this.database.taskDao().insetTask(TASK_DEMO);
        Task task = this.database.taskDao().getTasks().get(0);
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_ID);
    }

    @Test
    public void insertAndDeleteTask(){
        this.database.taskDao().insetTask(TASK_DEMO);
        Task task = this.database.taskDao().getTasks().get(0);
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_ID);
        this.database.taskDao().deleteTask(task);
        assertTrue(this.database.taskDao().getTasks().isEmpty());
    }
}