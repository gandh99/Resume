package com.example.resume.Work;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Work.class}, version = 1, exportSchema = false)
public abstract class WorkDatabase extends RoomDatabase {
  private static WorkDatabase instance;
  public abstract WorkDao workDao();

  public static WorkDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (WorkDatabase.class) {
        instance =
          Room
            .databaseBuilder(context.getApplicationContext(), WorkDatabase.class, "work_database")
            .fallbackToDestructiveMigration()
            .build();
      }
    }

    return instance;
  }
}
