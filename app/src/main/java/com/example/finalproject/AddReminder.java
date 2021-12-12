package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {

    Button btnSaveReminder;
    EditText etReminderName;
    EditText etDescription;
    EditText etDate;
    EditText etTime;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
        createNotificationChannel();

        dbHelper = new DatabaseHelper(AddReminder.this);
        btnSaveReminder = findViewById(R.id.btnSaveReminder);
        etReminderName = findViewById(R.id.etReminderName);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
    }

    /**
     * Button handler for the buttons on the Activity
     * @param view The button that is being clicked on
     */
    public void btnHandler(View view) {
        if (view.getId() == R.id.btnAddReminderBack){
            startActivity(new Intent(this, Reminders.class));
        }
        else if (view.getId() == R.id.btnSaveReminder){
            //TODO: Function to save the reminder to the Database
            String reminderNameStr = etReminderName.getText().toString();
            String reminderDescStr = etDescription.getText().toString();
            String reminderDateStr = etDate.getText().toString();
            String reminderTimeStr = etTime.getText().toString();
            // validating if the required fields are empty or not.
            /*if (reminderNameStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Name",
                Toast.LENGTH_SHORT).show();
                return;
            }*/

            ReminderModel reminder = new ReminderModel(reminderNameStr, reminderDescStr,
                    reminderDateStr, reminderTimeStr);
            dbHelper.addRecord(reminder);

            // Notification Starts
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddReminder.this, ReminderBroadcast.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("title",reminderNameStr);
            intent.putExtra("description",reminderDescStr);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminder.this,
                    0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();
            long tenSecondsInMillis = 1000 * 10;

            long reminderTimeInMillis  = convertToMilliseconds(etDate.getText().toString(), etTime.getText().toString());

            // send a notification in 10 seconds
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    timeAtButtonClick + reminderTimeInMillis,
                    pendingIntent);

        }

    }

    private long convertToMilliseconds(String date, String time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        Date objDate = null;
        try {
            objDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Expected final date format
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
        String finalDate = dateFormat2.format(objDate);

        SimpleDateFormat dateFormat3 = new SimpleDateFormat("MMddyyyy hhmmss");
        Date objDateTime = null;
        try {
            objDateTime = dateFormat3.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = Calendar.getInstance().getTime();

        long diff = objDateTime.getTime() - currentDate.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffSeconds + diffMinutes + diffHours + diffDays;
    }


    /**
     * Creates a notification channel for the reminder notification
     */
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