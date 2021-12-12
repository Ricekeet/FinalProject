package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class Reminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // toggles Night Mode for application
        if (globals.isDarkModeEnabled()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        //TODO: Need to fill the listReminders view from SQL Database
        ListView listView = (ListView) findViewById(R.id.listReminders);
        DatabaseHelper dbHelper = new DatabaseHelper(Reminders.this);

        List<ReminderModel> records = dbHelper.viewRecords();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.reminder_cell, records);
        listView.setAdapter(adapter);

    }
    /**
     * Button Handler for the buttons to go back a view and to add a Reminder
     * @param view The button that is being clicked
     */
    public void btnHandler(View view) {
        switch(view.getId()){
            case(R.id.btnRemindersBack):{
                startActivity(new Intent(this,MainActivity.class));
                break;
            }
            case(R.id.btnRemindersAdd):{
                startActivity(new Intent(this, AddReminder.class));
                break;
            }
        }
    }
}