package com.example.resume.Education;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "education_table")
public class Education {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String institution;
  private String qualification;
  private String period;
  private String description;

  public Education(String institution, String qualification, String period, String description) {
    this.institution = institution;
    this.qualification = qualification;
    this.period = period;
    this.description = description;
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

  public String getQualification() {
    return qualification;
  }

  public void setQualification(String qualification) {
    this.qualification = qualification;
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
