package com.example.resume.Utility;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.resume.Achievement.Achievement;
import com.example.resume.Achievement.AchievementListAdapter;
import com.example.resume.Achievement.AchievementViewModel;
import com.example.resume.Education.Education;
import com.example.resume.Education.EducationListAdapter;
import com.example.resume.Education.EducationViewModel;
import com.example.resume.Home.GroupAdapter;
import com.example.resume.Home.TitleAdapter;
import com.example.resume.MainActivity;
import com.example.resume.R;
import com.example.resume.Skill.Skill;
import com.example.resume.Skill.SkillListAdapter;
import com.example.resume.Skill.SkillViewModel;
import com.example.resume.Work.Work;
import com.example.resume.Work.WorkListAdapter;
import com.example.resume.Work.WorkViewModel;

import java.io.File;
import java.util.List;

public class ConverterActivity extends AppCompatActivity {
  // Common
  private GroupAdapter groupAdapter;
  private RecyclerView recyclerView;
  private Format format;

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


  // Format
  final static public String INTENT_FORMAT = "format";
  public enum Format {
    IMAGE,
    PDF
  }

  public ConverterActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_converter);

    // Get format
    format = (Format) getIntent().getSerializableExtra(INTENT_FORMAT);

    educationViewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
    educationViewModel.getAllEducation().observe(this, new Observer<List<Education>>() {
      @Override
      public void onChanged(List<Education> educations) {
        educationListAdapter.submitList(educations);
      }
    });

    workViewModel = ViewModelProviders.of(this).get(WorkViewModel.class);
    workViewModel.getAllWork().observe(this, new Observer<List<Work>>() {
      @Override
      public void onChanged(List<Work> works) {
        workListAdapter.submitList(works);
      }
    });

    achievementViewModel = ViewModelProviders.of(this).get(AchievementViewModel.class);
    achievementViewModel.getAllAchievements().observe(this, new Observer<List<Achievement>>() {
      @Override
      public void onChanged(List<Achievement> achievements) {
        achievementListAdapter.submitList(achievements);
      }
    });

    skillViewModel = ViewModelProviders.of(this).get(SkillViewModel.class);
    skillViewModel.getAllSkills().observe(this, new Observer<List<Skill>>() {
      @Override
      public void onChanged(List<Skill> skills) {
        skillListAdapter.submitList(skills);
      }
    });
    
    // Setup recyclerView
    recyclerView = findViewById(R.id.imageActivity_recyclerView);

    // Setup GroupAdapter
    GroupAdapter.Builder builder = new GroupAdapter.Builder();
    builder.add(new TitleAdapter(this, "Education"));
    builder.add(educationListAdapter);
    builder.add(new TitleAdapter(this, "Work"));
    builder.add(workListAdapter);
    builder.add(new TitleAdapter(this, "Achievements"));
    builder.add(achievementListAdapter);
    builder.add(new TitleAdapter(this, "Skills"));
    builder.add(skillListAdapter);
    groupAdapter = builder.build();

    // Set recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(groupAdapter);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater()
      .inflate(R.menu.toolbar_menu, menu);

    if (format == Format.IMAGE) {
      File file = ImageConverter.createImage(this, recyclerView);
      if (file != null) {
        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
      } else {
        Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
      }
    } else if (format == Format.PDF) {
      Bitmap bitmap = ImageConverter.getBitmap(recyclerView);
      boolean conversionSuccess = PDFConverter.createPdf(bitmap);
      if (conversionSuccess) {
        Toast.makeText(this, "PDF saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
      } else {
        Toast.makeText(this, "Error saving PDF", Toast.LENGTH_SHORT).show();
      }
    }

    return true;
  }

}
