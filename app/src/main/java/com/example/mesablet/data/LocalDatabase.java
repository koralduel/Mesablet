package com.example.mesablet.data;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mesablet.R;
import com.example.mesablet.entities.Post;
import com.example.mesablet.mesablet;


@Database(entities = {Post.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

    public abstract PostDao postDao();

    public static LocalDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(mesablet.context.getApplicationContext(),
                    LocalDatabase.class,
                    mesablet.context.getString(R.string.LocalDB)).build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
