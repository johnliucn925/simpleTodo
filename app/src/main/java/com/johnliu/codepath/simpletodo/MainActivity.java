package com.johnliu.codepath.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOExceptionWithCause;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnliu on 6/20/16.
 */
public class MainActivity extends Activity {
    private List<String> items;
    private final int ITEM_EDIT_REQUEST_CODE = 100;
    private ArrayAdapter<String> itemsAdapter;
    private EditText editText;
    private ListView lvItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateData();
        setupListViewListerner();
    }

    private void initiateData() {
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, items);
        lvItems.setAdapter(itemsAdapter);
    }

    private void setupListViewListerner() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return false;
                    }
                });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent editItem = new Intent(MainActivity.this, EditItemActivity.class);
                        editItem.putExtra("item", items.get(i));
                        editItem.putExtra("pos", i);
                        startActivityForResult(editItem, ITEM_EDIT_REQUEST_CODE);
                    }
                });
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String newItem = etNewItem.getText().toString();

        itemsAdapter.add(newItem);

        etNewItem.setText("");

        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ITEM_EDIT_REQUEST_CODE) {
            String newItem = data.getExtras().getString("item");
            int pos = data.getExtras().getInt("pos", 0);
            if (newItem != null && newItem.length() > 1 && !items.get(pos).equals(newItem)) {
                items.set(pos, newItem);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException ioException) {
            items = new ArrayList<>();
        }

    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}