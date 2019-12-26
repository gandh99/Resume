package com.example.resume.Work;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class WorkViewModel extends AndroidViewModel {
  private WorkRepository workRepository;
  private LiveData<List<Work>> allWork;
  
  public WorkViewModel(@NonNull Application application) {
    super(application);
    workRepository = new WorkRepository(application);
    allWork = workRepository.getAllWork();
  }

  public void insertWork(Work work) {
    workRepository.insert(work);
  }

  public void updateWork(Work work) {
    workRepository.update(work);
  }

  public void deleteWork(Work work) {
    workRepository.delete(work);
  }

  public LiveData<List<Work>> getAllWork() {
    return allWork;
  }
}
