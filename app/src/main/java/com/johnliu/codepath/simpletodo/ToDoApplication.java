package com.johnliu.codepath.simpletodo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;

/**
 * Created by johnliu on 6/27/16.
 */
public class ToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("todoItemsDb.db").create();

        ActiveAndroid.initialize(dbConfiguration);
    }
}
