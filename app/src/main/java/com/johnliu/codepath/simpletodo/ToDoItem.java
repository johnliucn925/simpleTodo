package com.johnliu.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by johnliu on 6/27/16.
 */
@Table(name="todoItems")
public class ToDoItem extends Model {
    // This is the unique id given by the server
    @Column(name = "itemId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long itemId;
    // This is a regular field
    @Column(name = "item")
    public String item;

    @Column(name = "created")
    public Date created;

    @Column(name = "updated")
    public Date updated;

    @Column(name = "targetDate")
    public Date targetDate;

    @Column(name = "status")
    public String status;

    // Make sure to have a default constructor for every ActiveAndroid model
    public ToDoItem(){
        super();
    }

    public ToDoItem(int id, String item, Date created, Date updated, Date targetDate, String status){
        super();
        this.itemId = id;
        this.item = item;
        this.created = created;
        this.updated = updated;
        this.targetDate = targetDate;
        this.status = status;

    }


    @Override
    public String toString(){
        return item;
    }

}
