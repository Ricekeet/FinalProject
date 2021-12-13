package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomAdapter  extends ArrayAdapter<ReminderModel> {

    Context custom_context;
    int custom_resource;
    public CustomAdapter(Context context, int resource, List<ReminderModel> reminderList) {
        super(context, resource, reminderList);
        custom_context = context;
        custom_resource = resource;
    }

    // Get Item to display on adapter
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int id = getItem(i).getId();
        String name = getItem(i).getName();
        String desc = getItem(i).getDescription();
        String date = getItem(i).getDate();
        String time = getItem(i).getTime();

        LayoutInflater inflater = LayoutInflater.from(custom_context);
        view = inflater.inflate(custom_resource, viewGroup, false);

        TextView rName = view.findViewById(R.id.lblReminderName);
        TextView rDate = view.findViewById(R.id.lblDateSet);
        TextView rTime = view.findViewById(R.id.lblTimeLeft);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        date = date.replaceAll("[/]","");
        time = time.replaceAll("[:]","");
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

        String timeLeft = diffDays + ":" + diffHours + ":" + diffMinutes + ":" +
                diffSeconds + " left";

        if(diffSeconds<0){
            rName.setText(name);
            rDate.setText("");
            rTime.setText("Done");
        }else{
            rName.setText(name);
            rDate.setText(finalDate);
            rTime.setText(timeLeft);
        }

        return view;
    }
}