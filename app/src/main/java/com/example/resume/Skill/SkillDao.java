package com.example.resume.Skill;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SkillDao {
  @Insert
  void insert(Skill skill);

  @Update
  void update(Skill skill);

  @Delete
  void delete(Skill skill);

  @Query("SELECT * FROM skill_table")
  LiveData<List<Skill>> getAllSkills();
}
