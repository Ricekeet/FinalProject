package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        ListView reminderList = (ListView) findViewById(R.id.listReminders);
        DatabaseHelper dbHelper = new DatabaseHelper(Reminders.this);

        List<ReminderModel> records = dbHelper.viewRecords();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.reminder_cell, records);
        reminderList.setAdapter(adapter);

        reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReminderModel reminderModel = (ReminderModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(Reminders.this, AddReminder.class);
                intent.putExtra("editFlag", true);
                intent.putExtra("ID", reminderModel.getId());
                intent.putExtra("name", reminderModel.getName());
                intent.putExtra("desc",reminderModel.getDescription());
                intent.putExtra("date",reminderModel.getDate());
                intent.putExtra("time",reminderModel.getTime());
                startActivity(intent);
            }
        });
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