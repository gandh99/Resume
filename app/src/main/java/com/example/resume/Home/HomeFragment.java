package com.example.resume.Home;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resume.Achievement.Achievement;
import com.example.resume.Achievement.AchievementListAdapter;
import com.example.resume.Achievement.AchievementViewModel;
import com.example.resume.Education.Education;
import com.example.resume.Education.EducationListAdapter;
import com.example.resume.Education.EducationViewModel;
import com.example.resume.R;
import com.example.resume.Skill.Skill;
import com.example.resume.Skill.SkillListAdapter;
import com.example.resume.Skill.SkillViewModel;
import com.example.resume.Work.Work;
import com.example.resume.Work.WorkListAdapter;
import com.example.resume.Work.WorkViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
  // Common
  private Activity activity;
  private GroupAdapter groupAdapter;

  // Education
  private EducationViewModel educationViewModel;
  private EducationListAdapter educationListAdapter = new EducationListAdapter();

  // Work
  private WorkViewModel workViewModel;
  private WorkListAdapter workListAdapter = new WorkListAdapter();

  // Achievement
  private AchievementViewModel achievementViewModel;
  private AchievementListAdapter achievementListAdapter = new AchievementListAdapter();

  // Skill
  private SkillViewModel skillViewModel;
  private SkillListAdapter skillListAdapter = new SkillListAdapter();


  public HomeFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    educationViewModel = ViewModelProviders.of(getActivity()).get(EducationViewModel.class);
    educationViewModel.getAllEducation().observe(this, new Observer<List<Education>>() {
      @Override
      public void onChanged(List<Education> educations) {
        educationListAdapter.submitList(educations);
      }
    });

    workViewModel = ViewModelProviders.of(getActivity()).get(WorkViewModel.class);
    workViewModel.getAllWork().observe(this, new Observer<List<Work>>() {
      @Override
      public void onChanged(List<Work> works) {
        workListAdapter.submitList(works);
      }
    });

    achievementViewModel = ViewModelProviders.of(getActivity()).get(AchievementViewModel.class);
    achievementViewModel.getAllAchievements().observe(this, new Observer<List<Achievement>>() {
      @Override
      public void onChanged(List<Achievement> achievements) {
        achievementListAdapter.submitList(achievements);
      }
    });

    skillViewModel = ViewModelProviders.of(getActivity()).get(SkillViewModel.class);
    skillViewModel.getAllSkills().observe(this, new Observer<List<Skill>>() {
      @Override
      public void onChanged(List<Skill> skills) {
        skillListAdapter.submitList(skills);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    RecyclerView recyclerView = view.findViewById(R.id.home_recyclerView);

    // Setup GroupAdapter
    GroupAdapter.Builder builder = new GroupAdapter.Builder();
    builder.add(new TitleAdapter(activity, "Education"));
    builder.add(educationListAdapter);
    builder.add(new TitleAdapter(activity, "Work"));
    builder.add(workListAdapter);
    builder.add(new TitleAdapter(activity, "Achievements"));
    builder.add(achievementListAdapter);
    builder.add(new TitleAdapter(activity, "Skills"));
    builder.add(skillListAdapter);
    groupAdapter = builder.build();

    // Set recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    recyclerView.setAdapter(groupAdapter);

    return view;
  }

}
