package com.example.resume.Achievement;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievement_table")
public class Achievement {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String title;
  private String year;
  private String description;

  public Achievement(String title, String year, String description) {
    this.title = title;
    this.year = year;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
