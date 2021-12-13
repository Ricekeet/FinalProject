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
import android.widget.TextView;
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
    Button btnDeleteReminder;
    EditText etReminderName;
    EditText etDescription;
    EditText etDate;
    EditText etTime;
    TextView lblAddReminderHeader;
    DatabaseHelper dbHelper;
    Boolean isEdit = false;
    int editID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
        createNotificationChannel();

        dbHelper = new DatabaseHelper(AddReminder.this);
        btnSaveReminder = findViewById(R.id.btnSaveReminder);
        btnDeleteReminder = findViewById(R.id.btnDeleteReminder);
        etReminderName = findViewById(R.id.etReminderName);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        lblAddReminderHeader = findViewById(R.id.lblAddReminderHeader);
        Intent intent = getIntent();
        Bundle playerBundle = intent.getExtras();
        btnDeleteReminder.setVisibility(View.GONE);
        if(playerBundle!=null)
        {
            editID = (int) playerBundle.get("ID");
            etReminderName.setText(playerBundle.get("name").toString());
            etDescription.setText(playerBundle.get("desc").toString());
            etDate.setText(playerBundle.get("date").toString());
            etTime.setText(playerBundle.get("time").toString());
            lblAddReminderHeader.setText("Edit");
            isEdit = true;
            btnDeleteReminder.setVisibility(View.VISIBLE);
        }
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
            String reminderNameStr = etReminderName.getText().toString();
            String reminderDescStr = etDescription.getText().toString();
            String reminderDateStr = etDate.getText().toString();
            String reminderTimeStr = etTime.getText().toString();

            // validating if the required fields are empty or not.
            if (reminderNameStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Name",
                Toast.LENGTH_SHORT).show();
                return;
            }
            if (reminderDateStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Date",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (reminderTimeStr.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter Time",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            //validate Date and Time format
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmss");
            reminderDateStr = reminderDateStr.replaceAll("[/]","");
            reminderTimeStr = reminderTimeStr.replaceAll("[:]","");
            Date objDate = null;
            try {
                objDate = dateFormat.parse(reminderDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please enter Date with proper format",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                objDate = timeFormat.parse(reminderTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please enter Time with proper format",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            ReminderModel reminder = new ReminderModel(reminderNameStr, reminderDescStr,
                    reminderDateStr, reminderTimeStr);
            if(isEdit){
                reminder = new ReminderModel(editID, reminderNameStr, reminderDescStr,
                        reminderDateStr, reminderTimeStr);
                dbHelper.updateReminder(reminder);
            }
            else{
                dbHelper.addRecord(reminder);
            }

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

            long reminderTimeInMillis  = convertToMilliseconds(etDate.getText().toString(), etTime.getText().toString());

            // send a notification in given time
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    timeAtButtonClick + reminderTimeInMillis,
                    pendingIntent);
        }else if(view.getId() == R.id.btnDeleteReminder){
            dbHelper.deleteReminder(editID);
            Toast.makeText(getApplicationContext(), "Reminder Deleted",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Reminders.class));
        }

        // Returns to reminders list
        startActivity(new Intent(this, Reminders.class));
    }


    /**
     * @param date String of date
     * @param time String of time
     * @return milliseconds value
     * This function converts scheduled date time to milliseconds difference from the current time
     */
    private long convertToMilliseconds(String date, String time){
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("MMddyyyy hhmmss");
        Date objDateTime = null;
        date = date.replaceAll("[/]","");
        time = time.replaceAll("[:]","");
        try {
            objDateTime = dateFormat3.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = Calendar.getInstance().getTime();

        long diff = objDateTime.getTime() - currentDate.getTime();

        return diff;
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