package com.example.clientlist.datebase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_list")
public class Client {
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "sec_name")
    private String sec_name;

    @ColumnInfo (name = "number")
    private String number;

    @ColumnInfo (name = "importance")
    private int importance;

    @ColumnInfo (name = "description")
    private String description;

    @ColumnInfo (name = "special")
    private int special;


    public Client(int id, String name, String sec_name, String number, int importance, String description, int special) {
        this.id = id;
        this.name = name;
        this.sec_name = sec_name;
        this.number = number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }

    @Ignore
    public Client(String name, String sec_name, String number, int importance, String description, int special) {
        this.name = name;
        this.sec_name = sec_name;
        this.number = number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
