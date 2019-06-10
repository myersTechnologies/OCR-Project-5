package com.cleanup.todoc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cleanup.todoc.model.Task;

public class TaskDataBase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "TodocTask.db";
    public String TABLE_NAME = "Task_table";

    public static String COL_1 = "ID";
    public static String COL_2 = "PROJECT";
    public static String COL_3 = "TASK";
    public static String COL_4 = "DATE";
    public SQLiteDatabase db;


    public TaskDataBase(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID LONG, PROJECT LONG, TASK TEXT, DATE LONG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTaskData(Task task){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, task.getId());
        contentValues.put(COL_2, task.getProject().getId());
        contentValues.put(COL_3, task.getName());
        contentValues.put(COL_4, task.getCreationTimestamp());
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        } else {
            return true;
        }

    }

    public Cursor getAllTasks(){
        db = this.getWritableDatabase();
        Cursor taskCursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        return  taskCursor;
    }

    public Integer deleteSelectedTask(long id){
        db = this.getWritableDatabase();
        int taskId = db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
        return taskId;
    }

    public int getMaxID(){
        db = getReadableDatabase();
        Cursor maxIdTracker = db.rawQuery("Select NULLIF(max(" + COL_1 + "),0) from " + TABLE_NAME, null);
        int max = 0;
        if (maxIdTracker.getCount() > 0){
            maxIdTracker.moveToNext();
            max = maxIdTracker.getInt(0);
        }
        maxIdTracker.close();
        return max +1;
    }
}
