package com.johnliu.codepath.simpletodo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by johnliu on 6/20/16.
 */
public class MainActivity extends Activity{
    final Context context = this;
    private List<ToDoItem> items;
    private final int ITEM_EDIT_REQUEST_CODE = 100;
    static final int DATE_DIALOG_ID = 999;
    private ArrayAdapter<ToDoItem> itemsAdapter;
    private EditText editText;
    private ListView lvItems;
    private DatePickerDialog datePickerDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateData();
        setupListViewListerner();
    }

    private void initiateData() {
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<ToDoItem>();
        readItems();
        itemsAdapter = new ToDoItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
    }

    TextView dueDate = null;
    EditText text = null;
    private void setupListViewListerner() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ToDoItem delItem = items.get(i);
                        delItem.delete();
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dueDate.setText(getDateAsString(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.activity_edit_item);
                        dialog.setTitle("Edit task");


                        // set the custom dialog components - text, image and button
                        text = (EditText) dialog.findViewById(R.id.editText);
                        text.setText(items.get(i).item);

                        dueDate = (TextView) dialog.findViewById(R.id.duedate);
                        dueDate.setText(getDateAsString(items.get(i).targetDate));

                        dueDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                datePickerDialog.show();
                            }
                        });

                        Button dialogButton = (Button) dialog.findViewById(R.id.btnSave);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String newItem = text.getText().toString();
                                Date newDueDate = getDateFromString(dueDate.getText().toString());
                                int pos = i;
                                if (newItem != null && newItem.length() > 1 && !items.get(pos).equals(newItem)) {
                                    items.set(pos, getToDoItem(newItem, newDueDate));
                                    itemsAdapter.notifyDataSetChanged();
                                }

                                dialog.dismiss();
                            }
                        });
                        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
                        // if button is clicked, close the custom dialog
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        int position = text.getText().length();
                        Editable etext = text.getText();
                        Selection.setSelection(etext, position);
                        dialog.show();
                    }
                });
    }



    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar calendar =  Calendar.getInstance();
            calendar.set(selectedYear +1900, selectedMonth, selectedDay);
            // set selected date into textview
            dueDate.setText(getDateAsString(calendar.getTime()));
        }
    };

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String newItem = etNewItem.getText().toString();
        itemsAdapter.add(getToDoItem(newItem, new Date()));

        etNewItem.setText("");
    }

    private ToDoItem getToDoItem(String item, Date todoDueDate){
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.item = item;
        toDoItem.created = new Date();
        toDoItem.updated = new Date();
        toDoItem.targetDate = todoDueDate;
        toDoItem.status = "new";
        toDoItem.save();
        return toDoItem;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ITEM_EDIT_REQUEST_CODE) {
            String newItem = data.getExtras().getString("item");
            int pos = data.getExtras().getInt("pos", 0);
            if (newItem != null && newItem.length() > 1 && !items.get(pos).equals(newItem)) {
                items.set(pos, getToDoItem(newItem, new Date()));
                itemsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void readItems() {
        items =  new Select().from(ToDoItem.class).orderBy("updated").execute();
    }

    private String getDateAsString(Date date) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat.format(date);
    }
    private Date getDateFromString(String dateString) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

}