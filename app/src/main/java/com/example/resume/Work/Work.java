package com.example.resume.Work;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "work_table")
public class Work {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String company;
  private String position;
  private String period;
  private String description;

  public Work(String company, String position, String period, String description) {
    this.company = company;
    this.position = position;
    this.period = period;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
