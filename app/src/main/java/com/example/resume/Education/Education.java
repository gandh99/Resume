package com.example.resume.Education;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "education_table")
public class Education {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String institution;
  private String period;

  public Education(int id, String institution, String period) {
    this.id = id;
    this.institution = institution;
    this.period = period;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }
}
