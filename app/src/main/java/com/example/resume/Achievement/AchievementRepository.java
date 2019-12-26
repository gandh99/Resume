package com.example.resume.Achievement;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

public class AchievementRepository {
  private AchievementDao achievementDao;
  private LiveData<List<Achievement>> allAchievements;

  public AchievementRepository(Application application) {
    AchievementDatabase database = AchievementDatabase.getInstance(application);
    achievementDao = database.achievementDao();
    allAchievements = achievementDao.getAllAchievements();
  }

  public void insert(Achievement achievement) {
    new InsertAchievementAsyncTask(achievementDao).execute(achievement);
  }

  public void update(Achievement achievement) {
    new UpdateAchievementAsyncTask(achievementDao).execute(achievement);
  }

  public void delete(Achievement achievement) {
    new DeleteAchievementAsyncTask(achievementDao).execute(achievement);
  }

  public LiveData<List<Achievement>> getAllAchievements() {
    return allAchievements;
  }

  private static class InsertAchievementAsyncTask extends AsyncTask<Achievement, Void, Void> {
    private AchievementDao achievementDao;

    private InsertAchievementAsyncTask(AchievementDao achievementDao) {
      this.achievementDao = achievementDao;
    }

    @Override
    protected Void doInBackground(Achievement... achievements) {
      achievementDao.insert(achievements[0]);
      return null;
    }
  }

  private static class UpdateAchievementAsyncTask extends AsyncTask<Achievement, Void, Void> {
    private AchievementDao achievementDao;

    private UpdateAchievementAsyncTask(AchievementDao achievementDao) {
      this.achievementDao = achievementDao;
    }

    @Override
    protected Void doInBackground(Achievement... achievements) {
      achievementDao.update(achievements[0]);
      return null;
    }
  }

  private static class DeleteAchievementAsyncTask extends AsyncTask<Achievement, Void, Void> {
    private AchievementDao achievementDao;

    private DeleteAchievementAsyncTask(AchievementDao achievementDao) {
      this.achievementDao = achievementDao;
    }

    @Override
    protected Void doInBackground(Achievement... achievements) {
      achievementDao.delete(achievements[0]);
      return null;
    }
  }
}
