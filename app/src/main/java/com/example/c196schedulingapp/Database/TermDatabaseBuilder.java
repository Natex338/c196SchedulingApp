package com.example.c196schedulingapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196schedulingapp.DAO.TermDAO;
import com.example.c196schedulingapp.Entity.Term;

@Database(entities = {Term.class}, version = 1, exportSchema = false)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();

    private static volatile TermDatabaseBuilder INSTANCE;

    static TermDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (TermDatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TermDatabaseBuilder.class,"MyTermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

