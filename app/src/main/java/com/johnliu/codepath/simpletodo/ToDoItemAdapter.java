package com.johnliu.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by johnliu on 6/29/16.
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {
    public ToDoItemAdapter(Context context, List<ToDoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem toDoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todoitem, parent, false);
        }
        // Lookup view for data population
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvTarget = (TextView) convertView.findViewById(R.id.tvTarget);
        // Populate the data into the template view using the data object
        tvItem.setText(toDoItem.item);
        tvTarget.setText(getDateAsString(toDoItem.targetDate));
        // Return the completed view to render on screen
        return convertView;
    }


    private String getDateAsString(Date date) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
}
