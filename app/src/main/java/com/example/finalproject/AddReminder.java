package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
    }

    public void btnHandler(View view) {
        if (view.getId() == R.id.btnAddReminderBack){
            startActivity(new Intent(this, Reminders.class));
        }
        else if (view.getId() == R.id.btnSaveReminder){
            //TODO: Function to save the reminder to the Database
        }
    }
}