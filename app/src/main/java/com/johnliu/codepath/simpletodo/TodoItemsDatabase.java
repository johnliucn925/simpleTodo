package com.johnliu.codepath.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by johnliu on 6/25/16.
 */
public class TodoItemsDatabase extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoItemsDb";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEMS= "items";

    // Item Table Columns
    private static final String KEY_ITEM_ID = "id";

    public TodoItemsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE todoItems(id INTEGER PRIMARY KEY, item varchar(200), created DATETIME DEFAULT CURRENT_TIMESTAMP, updated DATETIME DEFAULT CURRENT_TIMESTAMP, targetDate DATETIME DEFAULT CURRENT_TIMESTAMP,status varchar(10))";
        sqLiteDatabase.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS todoItems");
            onCreate(sqLiteDatabase);
        }
    }
}
