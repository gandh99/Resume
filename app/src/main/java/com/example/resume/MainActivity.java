package com.example.resume;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.resume.Achievement.AchievementFragment;
import com.example.resume.Education.EducationFragment;
import com.example.resume.Education.EducationViewModel;
import com.example.resume.Skill.SkillFragment;
import com.example.resume.Work.WorkFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
  private DrawerLayout drawerLayout;
  private Toolbar toolbar;
  private NavigationView navigationView;
  private EducationViewModel educationViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewReferences();
    setupViewModel();
    setupActionBar();
    setupNavigationView();
    setupInitialFragmentView(savedInstanceState);
  }

  private void findViewReferences() {
    drawerLayout = findViewById(R.id.drawer_layout);
    toolbar = findViewById(R.id.toolbar);
    navigationView = findViewById(R.id.nav_view);
  }

  private void setupViewModel() {
    educationViewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  private void setupActionBar() {
    setSupportActionBar(toolbar);
    ActionBarDrawerToggle actionBarDrawerToggle =
      new ActionBarDrawerToggle(this, drawerLayout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();
  }

  private void setupNavigationView() {
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
          case R.id.home:
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new HomeFragment())
              .commit();
            break;

          case R.id.education:
            toolbar.setTitle(menuItem.getTitle());
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new EducationFragment(MainActivity.this))
              .commit();
            break;

          case R.id.work:
            toolbar.setTitle(menuItem.getTitle());
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new WorkFragment(MainActivity.this))
              .commit();
            break;

          case R.id.achievements:
            toolbar.setTitle(menuItem.getTitle());
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new AchievementFragment(MainActivity.this))
              .commit();
            break;

          case R.id.skills:
            toolbar.setTitle(menuItem.getTitle());
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new SkillFragment(MainActivity.this))
              .commit();
            break;

          case R.id.share:
            Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
            break;

          case R.id.send:
            Toast.makeText(MainActivity.this, "Send", Toast.LENGTH_SHORT).show();
            break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
      }
    });
  }

  private void setupInitialFragmentView(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_container, new HomeFragment())
        .commit();
      navigationView.setCheckedItem(R.id.home);
    }
  }
}
