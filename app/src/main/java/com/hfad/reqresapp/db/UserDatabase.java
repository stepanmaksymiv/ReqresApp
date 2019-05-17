package com.hfad.reqresapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.hfad.reqresapp.model.Data;


@Database(entities = {Data.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "users.db";
    private static volatile UserDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context){

        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DB_NAME).build();
                }
            }
        }
        return instance;
    }
}
