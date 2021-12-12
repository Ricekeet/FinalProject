package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter  extends ArrayAdapter<ReminderModel> {

    Context custom_context;
    int custom_resource;
    public CustomAdapter(Context context, int resource, List<ReminderModel> reminderList) {
        super(context, resource, reminderList);
        custom_context = context;
        custom_resource = resource;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int id = getItem(i).getId();
        String name = getItem(i).getName();
        String desc = getItem(i).getName();
        String date = getItem(i).getName();
//        int ties = getItem(i).getTies();

        LayoutInflater inflater = LayoutInflater.from(custom_context);
        view = inflater.inflate(custom_resource, viewGroup, false);

        TextView rid = view.findViewById(R.id.txtID);
        TextView rName = view.findViewById(R.id.txtName);
        TextView rDesc = view.findViewById(R.id.txtDesc);
        TextView rDate = view.findViewById(R.id.txtDate);

        rid.setText(Integer.toString(id));
        rName.setText(name);
        rDesc.setText(desc);
        rDate.setText(date);

        return view;
    }
}