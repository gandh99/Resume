package com.example.resume;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.resume.Achievement.AchievementFragment;
import com.example.resume.Education.EducationFragment;
import com.example.resume.Education.EducationViewModel;
import com.example.resume.Home.HomeFragment;
import com.example.resume.Skill.SkillFragment;
import com.example.resume.Utility.ConverterActivity;
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
            toolbar.setTitle(menuItem.getTitle());
            getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
              .commit();
            break;

          case R.id.summary:
            // TODO
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
        .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
        .commit();
      navigationView.setCheckedItem(R.id.home);
      toolbar.setTitle("Home");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater()
      .inflate(R.menu.toolbar_menu, menu);

    return true;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save_as_image:
        /* ConverterActivity will start a new activity and generate the layout to be saved as an image
        * When image has been saved, the activity will automatically return to MainActivity */
        Intent intentSaveImage = new Intent(MainActivity.this, ConverterActivity.class);
        intentSaveImage.putExtra(ConverterActivity.INTENT_FORMAT, ConverterActivity.Format.IMAGE);
        startActivity(intentSaveImage);
        return true;

      case R.id.action_save_as_pdf:
        /* ConverterActivity will start a new activity and generate the layout to be saved as an image
         * When image has been saved, the activity will automatically return to MainActivity */
        Intent intentSavePDF = new Intent(MainActivity.this, ConverterActivity.class);
        intentSavePDF.putExtra(ConverterActivity.INTENT_FORMAT, ConverterActivity.Format.PDF);
        startActivity(intentSavePDF);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
