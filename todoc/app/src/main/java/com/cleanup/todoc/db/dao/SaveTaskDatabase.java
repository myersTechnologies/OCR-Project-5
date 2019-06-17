package com.cleanup.todoc.db.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class SaveTaskDatabase extends RoomDatabase {
    public static volatile SaveTaskDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static SaveTaskDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SaveTaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveTaskDatabase.class, "TodocDatabase.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    public int setMaxIndex(){
        int max = taskDao().getTasks().size();
        return max + 1;
    }

}
