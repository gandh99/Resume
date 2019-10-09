package com.example.resume.Skill;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.resume.Skill.Skill;

import java.util.List;

public class SkillViewModel extends AndroidViewModel {
  private SkillRepository skillRepository;
  private LiveData<List<Skill>> allSkills;

  public SkillViewModel(@NonNull Application application) {
    super(application);
    skillRepository = new SkillRepository(application);
    allSkills = skillRepository.getAllSkills();
  }

  public void insertSkill(Skill skill) {
    skillRepository.insert(skill);
  }

  public void updateSkill(Skill skill) {
    skillRepository.update(skill);
  }

  public void deleteSkill(Skill skill) {
    skillRepository.delete(skill);
  }

  public LiveData<List<Skill>> getAllSkills() {
    return allSkills;
  }
}
