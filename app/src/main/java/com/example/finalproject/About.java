package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    /**
     * Button Handler for going back
     * @param view The button that is being clicked on
     */
    public void btnHandler(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}