package com.example.resume.Achievement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AchievementDao {
  @Insert
  void insert(Achievement achievement);

  @Update
  void update(Achievement achievement);

  @Delete
  void delete(Achievement achievement);

  @Query("SELECT * FROM achievement_table")
  LiveData<List<Achievement>> getAllAchievements();
}
