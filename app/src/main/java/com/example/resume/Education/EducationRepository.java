package com.example.resume.Education;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EducationRepository {
  private EducationDao educationDao;
  private LiveData<List<Education>> allEducation;

  public EducationRepository(Application application) {
    EducationDatabase database = EducationDatabase.getInstance(application);
    educationDao = database.educationDao();
    allEducation = educationDao.getAllEducation();
  }

  public void insert(Education education) {
    new InsertEducationAsyncTask(educationDao).execute(education);
  }

  public void update(Education education) {
    new UpdateEducationAsyncTask(educationDao).execute(education);
  }

  public void delete(Education education) {
    new DeleteEducationAsyncTask(educationDao).execute(education);
  }

  public LiveData<List<Education>> getAllEducation() {
    return allEducation;
  }

  private static class InsertEducationAsyncTask extends AsyncTask<Education, Void, Void> {
    private EducationDao educationDao;

    private InsertEducationAsyncTask(EducationDao educationDao) {
      this.educationDao = educationDao;
    }

    @Override
    protected Void doInBackground(Education... educations) {
      educationDao.insert(educations[0]);
      return null;
    }
  }

  private static class UpdateEducationAsyncTask extends AsyncTask<Education, Void, Void> {
    private EducationDao educationDao;

    private UpdateEducationAsyncTask(EducationDao educationDao) {
      this.educationDao = educationDao;
    }

    @Override
    protected Void doInBackground(Education... educations) {
      educationDao.update(educations[0]);
      return null;
    }
  }

  private static class DeleteEducationAsyncTask extends AsyncTask<Education, Void, Void> {
    private EducationDao educationDao;

    private DeleteEducationAsyncTask(EducationDao educationDao) {
      this.educationDao = educationDao;
    }

    @Override
    protected Void doInBackground(Education... educations) {
      educationDao.delete(educations[0]);
      return null;
    }
  }
}
