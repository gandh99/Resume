package com.example.resume.Skill;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SkillRepository {
  private SkillDao skillDao;
  private LiveData<List<Skill>> allSkills;

  public SkillRepository(Application application) {
    SkillDatabase database = SkillDatabase.getInstance(application);
    skillDao = database.skillDao();
    allSkills = skillDao.getAllSkills();
  }

  public void insert(Skill skill) {
    new InsertSkillAsyncTask(skillDao).execute(skill);
  }

  public void update(Skill skill) {
    new UpdateSkillAsyncTask(skillDao).execute(skill);
  }

  public void delete(Skill skill) {
    new DeleteSkillAsyncTask(skillDao).execute(skill);
  }

  public LiveData<List<Skill>> getAllSkills() {
    return allSkills;
  }

  private static class InsertSkillAsyncTask extends AsyncTask<Skill, Void, Void> {
    private SkillDao skillDao;

    private InsertSkillAsyncTask(SkillDao skillDao) {
      this.skillDao = skillDao;
    }

    @Override
    protected Void doInBackground(Skill... skills) {
      skillDao.insert(skills[0]);
      return null;
    }
  }

  private static class UpdateSkillAsyncTask extends AsyncTask<Skill, Void, Void> {
    private SkillDao skillDao;

    private UpdateSkillAsyncTask(SkillDao skillDao) {
      this.skillDao = skillDao;
    }

    @Override
    protected Void doInBackground(Skill... skills) {
      skillDao.update(skills[0]);
      return null;
    }
  }

  private static class DeleteSkillAsyncTask extends AsyncTask<Skill, Void, Void> {
    private SkillDao skillDao;

    private DeleteSkillAsyncTask(SkillDao skillDao) {
      this.skillDao = skillDao;
    }

    @Override
    protected Void doInBackground(Skill... skills) {
      skillDao.delete(skills[0]);
      return null;
    }
  }
}
