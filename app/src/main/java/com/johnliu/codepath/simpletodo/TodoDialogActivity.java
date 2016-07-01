package com.johnliu.codepath.simpletodo;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by johnliu on 6/29/16.
 */
public class TodoDialogActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        showEditDialog();
    }

    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        EditItemDialogFragment editItemDialogFragment = EditItemDialogFragment.newInstance("Edit your todo item");
        editItemDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(ToDoItem inputText) {
        Toast.makeText(this, "Hi, " + inputText.item, Toast.LENGTH_SHORT).show();

    }
}