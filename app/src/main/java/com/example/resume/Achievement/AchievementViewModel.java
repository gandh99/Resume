package com.example.resume.Achievement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AchievementViewModel extends AndroidViewModel {
  private AchievementRepository repository;
  private LiveData<List<Achievement>> allAchievements;

  public AchievementViewModel(@NonNull Application application) {
    super(application);
    repository = new AchievementRepository(application);
    allAchievements = repository.getAllAchievements();
  }

  public void insertAchievement(Achievement achievement) {
    repository.insert(achievement);
  }

  public void updateAchievement(Achievement achievement) {
    repository.update(achievement);
  }

  public void deleteAchievement(Achievement achievement) {
    repository.delete(achievement);
  }

  public LiveData<List<Achievement>> getAllAchievements() {
    return allAchievements;
  }
}
