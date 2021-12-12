package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Constant variables for database
    public static final String REMINDER_TABLE = "REMINDER";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_DATETIME = "DATETIME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "reminder.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPlayerTable = "CREATE TABLE " + REMINDER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, "  + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATETIME + " TEXT)";
        sqLiteDatabase.execSQL(createPlayerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE);
        onCreate(sqLiteDatabase);
    }

    // add new Reminder Record to SQLite Database
    public void addRecord(ReminderModel reminderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, reminderModel.getName());
        cv.put(COLUMN_DESCRIPTION, reminderModel.getDescription());
        cv.put(COLUMN_DATETIME, reminderModel.getDate());

        db.insert(REMINDER_TABLE, null, cv);
        db.close();
    }

    // Read data from Player Table
    public List<ReminderModel> viewRecords(){
        List<ReminderModel> viewList = new ArrayList<>();
        String queryString = "SELECT * FROM " + REMINDER_TABLE + " ORDER BY " + COLUMN_DATETIME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst())
        {
            do {
                int playerID = cursor.getInt(0);
                String reminderName = cursor.getString(1);
                String reminderDesc = cursor.getString(1);
                String reminderDate = cursor.getString(1);

                ReminderModel reminderModel = new ReminderModel(playerID,reminderName,reminderDesc,reminderDate);
                viewList.add(reminderModel);

            }while (cursor.moveToNext());

        }
        cursor.close();
        return viewList;
    }
}
