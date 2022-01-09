package com.example.c196schedulingapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.c196schedulingapp.DAO.CourseDAO;
import com.example.c196schedulingapp.DAO.TermDAO;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;

@Database(entities = {Term.class, Course.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();

    private static volatile AppDatabaseBuilder INSTANCE;



    static AppDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (AppDatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDatabaseBuilder.class,"MyTermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

