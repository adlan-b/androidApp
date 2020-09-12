package com.example.clientlist.datebase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "client_list_db";
    private static AppDataBase instanceDb;

    public static AppDataBase getInstanceDb(Context context) {
        if (instanceDb == null) {
            synchronized (LOCK) {
                instanceDb = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, AppDataBase.DATABASE_NAME).build();

            }
        }

        return instanceDb;

    }

    public abstract ClientDAO clientDAO();

}
