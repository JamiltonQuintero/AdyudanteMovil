package com.jamilton.mvvmlivedata;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")

public class Note {

@PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String descripcion;

    private int priority;

    public Note(String title, String descripcion, int priority) {
        this.title = title;
        this.descripcion = descripcion;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPriority() {
        return priority;
    }
}
