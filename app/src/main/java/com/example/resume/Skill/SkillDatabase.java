package com.example.resume.Skill;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Skill.class}, version = 1, exportSchema = false)
public abstract class SkillDatabase extends RoomDatabase {
  private static SkillDatabase instance;
  public abstract SkillDao skillDao();

  public static SkillDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (SkillDatabase.class) {
        instance =
          Room
            .databaseBuilder(context.getApplicationContext(), SkillDatabase.class, "skill_database")
            .fallbackToDestructiveMigration()
            .build();
      }
    }

    return instance;
  }
}
