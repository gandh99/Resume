package com.example.resume.Education;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EducationViewModel extends AndroidViewModel {
  private EducationRepository educationRepository;
  private LiveData<List<Education>> allEducation;

  public EducationViewModel(@NonNull Application application) {
    super(application);
    educationRepository = new EducationRepository(application);
    allEducation = educationRepository.getAllEducation();
  }

  public void insertEducation(Education education) {
    educationRepository.insert(education);
  }

  public void updateEducation(Education education) {
    educationRepository.update(education);
  }

  public void deleteEducation(Education education) {
    educationRepository.delete(education);
  }

  public LiveData<List<Education>> getAllEducation() {
    return allEducation;
  }
}
