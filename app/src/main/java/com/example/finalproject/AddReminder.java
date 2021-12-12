package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReminder extends AppCompatActivity {

    Button btnSaveReminder;
    EditText etReminderName;
    EditText etDescription;
    EditText etDate;
    EditText etTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
        createNotificationChannel();

        btnSaveReminder = findViewById(R.id.btnSaveReminder);
        etReminderName = findViewById(R.id.etReminderName);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
    }

    public void btnHandler(View view) {
        if (view.getId() == R.id.btnAddReminderBack){
            startActivity(new Intent(this, Reminders.class));
        }
        else if (view.getId() == R.id.btnSaveReminder){
            //TODO: Function to save the reminder to the Database


            // Notification Starts
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddReminder.this, ReminderBroadcast.class);
            intent.putExtra("title",etReminderName.getText());
            intent.putExtra("desc",etDescription.getText());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminder.this,0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();
            long tenSecondsInMillis = 1000 * 10;

            // send a notification in 10 seconds
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    timeAtButtonClick + tenSecondsInMillis,
                    pendingIntent);

        }

    }

    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}