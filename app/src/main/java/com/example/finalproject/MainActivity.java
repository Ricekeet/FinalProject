package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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