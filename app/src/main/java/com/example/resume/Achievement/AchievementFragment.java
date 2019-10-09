package com.example.resume.Achievement;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resume.MainActivity;
import com.example.resume.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private AchievementListAdapter adapter = new AchievementListAdapter();
  private AchievementViewModel achievementViewModel;
  private Activity activity;
  final private int DIALOG_REQUEST_ADD_CODE = 1;
  final private int DIALOG_REQUEST_UPDATE_CODE = 2;

  public AchievementFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    achievementViewModel = ViewModelProviders.of(getActivity()).get(AchievementViewModel.class);
    achievementViewModel.getAllAchievements().observe(this, new Observer<List<Achievement>>() {
      @Override
      public void onChanged(List<Achievement> achievements) {
        adapter.submitList(achievements);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_achievement, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_achievement);
    floatingActionButton = view.findViewById(R.id.fab_add_achievement);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    // Add achievement
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        AddAchievementDialog dialog = new AddAchievementDialog();
        dialog.setTargetFragment(AchievementFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Add Achievement");
      }
    });

    // Swipe to delete achievement
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
      | ItemTouchHelper.RIGHT ) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Achievement achievement = adapter.getItemAt(viewHolder.getAdapterPosition());
        achievementViewModel.deleteAchievement(achievement);
      }
    }).attachToRecyclerView(recyclerView);

    // For updating achievement
    adapter.setOnItemClickListener(new AchievementListAdapter.OnItemClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onItemClick(Achievement achievement) {
        Bundle bundle = new Bundle();
        bundle.putInt(AddAchievementDialog.INTENT_ID, achievement.getId());
        bundle.putString(AddAchievementDialog.INTENT_TITLE, achievement.getTitle());
        bundle.putString(AddAchievementDialog.INTENT_YEAR, achievement.getYear());
        bundle.putString(AddAchievementDialog.INTENT_DESCRIPTION, achievement.getDescription());

        AddAchievementDialog dialog = new AddAchievementDialog();
        dialog.setArguments(bundle);
        dialog.setTargetFragment(AchievementFragment.this, DIALOG_REQUEST_UPDATE_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Edit Achievement");
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      String title = data.getStringExtra(AddAchievementDialog.INTENT_TITLE);
      String year = data.getStringExtra(AddAchievementDialog.INTENT_YEAR);
      String description = data.getStringExtra(AddAchievementDialog.INTENT_DESCRIPTION);
      Achievement achievement = new Achievement(title, year, description);

      if (requestCode == DIALOG_REQUEST_ADD_CODE) {
        achievementViewModel.insertAchievement(achievement);
      } else if (requestCode == DIALOG_REQUEST_UPDATE_CODE) {
        int id = data.getIntExtra(AddAchievementDialog.INTENT_ID, 0);
        achievement.setId(id);
        achievementViewModel.updateAchievement(achievement);
      }
    }
  }
}
