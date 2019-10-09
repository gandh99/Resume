package com.example.resume.Achievement;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Achievement.class}, version = 1, exportSchema = false)
public abstract class AchievementDatabase extends RoomDatabase {
  private static AchievementDatabase instance;
  public abstract AchievementDao achievementDao();

  public static AchievementDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (Achievement.class) {
        instance =
          Room
            .databaseBuilder(context.getApplicationContext(), AchievementDatabase.class, "achievement_database")
            .fallbackToDestructiveMigration()
            .build();
      }
    }

    return instance;
  }
}
