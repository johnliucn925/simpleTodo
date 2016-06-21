package com.johnliu.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String item = getIntent().getStringExtra("item");
        pos = getIntent().getIntExtra("pos", 0);
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(item);

        int position = item.length();
        Editable etext = editText.getText();
        Selection.setSelection(etext, position);
    }

    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.editText);

        Intent data = new Intent();
        data.putExtra("item", etName.getText().toString());
        data.putExtra("pos", pos);

        setResult(RESULT_OK, data);
        this.finish();
    }
}
