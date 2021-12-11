package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globals.setDarkModeEnabled(sp.getBoolean("swDarkMode",false));
        globals.setPushNotifications(sp.getBoolean("swPushNotifications",false));

        // toggles Night Mode for application
        if (globals.isDarkModeEnabled()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    public void btnHandler(View view) {
        switch (view.getId()){
            case(R.id.btnReminderList):{
                startActivity(new Intent(this,Reminders.class));
                break;
            }
            case(R.id.btnSettings):{
                startActivity(new Intent(this,Settings.class));
                break;
            }
            case(R.id.btnAbout):{
                break;
            }
            case(R.id.btnWidgets):{
                break;
            }
        }
    }
}