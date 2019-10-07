package com.example.resume.Education;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EducationDao {
  @Insert
  void insert(Education education);

  @Update
  void update(Education education);

  @Delete
  void delete(Education education);

  @Query("SELECT * FROM education_table")
  LiveData<List<Education>> getAllEducation();
}
