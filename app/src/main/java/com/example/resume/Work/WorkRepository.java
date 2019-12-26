package com.example.resume.Work;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WorkRepository {
  private WorkDao workDao;
  private LiveData<List<Work>> allWork;
  
  public WorkRepository(Application application) {
    WorkDatabase database = WorkDatabase.getInstance(application);
    workDao = database.workDao();
    allWork = workDao.getAllWork();
  }

  public void insert(Work work) {
    new InsertWorkAsyncTask(workDao).execute(work);
  }

  public void update(Work work) {
    new UpdateWorkAsyncTask(workDao).execute(work);
  }

  public void delete(Work work) {
    new DeleteWorkAsyncTask(workDao).execute(work);
  }

  public LiveData<List<Work>> getAllWork() {
    return allWork;
  }

  private static class InsertWorkAsyncTask extends AsyncTask<Work, Void, Void> {
    private WorkDao workDao;

    private InsertWorkAsyncTask(WorkDao workDao) {
      this.workDao = workDao;
    }

    @Override
    protected Void doInBackground(Work... works) {
      workDao.insert(works[0]);
      return null;
    }
  }

  private static class UpdateWorkAsyncTask extends AsyncTask<Work, Void, Void> {
    private WorkDao workDao;

    private UpdateWorkAsyncTask(WorkDao workDao) {
      this.workDao = workDao;
    }

    @Override
    protected Void doInBackground(Work... works) {
      workDao.update(works[0]);
      return null;
    }
  }

  private static class DeleteWorkAsyncTask extends AsyncTask<Work, Void, Void> {
    private WorkDao workDao;

    private DeleteWorkAsyncTask(WorkDao workDao) {
      this.workDao = workDao;
    }

    @Override
    protected Void doInBackground(Work... works) {
      workDao.delete(works[0]);
      return null;
    }
  }
}
