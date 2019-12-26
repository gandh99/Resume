package com.example.resume.Skill;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skill_table")
public class Skill {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String title;
  private String proficiency;
  private String description;

  public Skill(String title, String proficiency, String description) {
    this.title = title;
    this.proficiency = proficiency;
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

  public String getProficiency() {
    return proficiency;
  }

  public void setProficiency(String proficiency) {
    this.proficiency = proficiency;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
