package com.example.resume.Education;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Education.class}, version = 4)
public abstract class EducationDatabase extends RoomDatabase {
  private static EducationDatabase instance;
  public abstract EducationDao educationDao();

  public static EducationDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (EducationDatabase.class) {
        instance =
          Room
            .databaseBuilder(context.getApplicationContext(), EducationDatabase.class, "education_database")
            .fallbackToDestructiveMigration()
            .build();
      }
    }

    return instance;
  }
}
