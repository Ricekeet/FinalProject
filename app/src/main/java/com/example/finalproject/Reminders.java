package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }

    public void btnHandler(View view) {
        switch(view.getId()){
            case(R.id.btnRemindersBack):{
                startActivity(new Intent(this,MainActivity.class));
            }
            case(R.id.btnRemindersAdd):{
                break;
            }
        }
    }
}