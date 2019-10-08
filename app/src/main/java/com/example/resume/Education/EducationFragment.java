package com.example.resume.Education;


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
public class EducationFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private EducationListAdapter adapter = new EducationListAdapter();
  private EducationViewModel educationViewModel;
  private Activity activity;
  final private int DIALOG_REQUEST_ADD_CODE = 1;
  final private int DIALOG_REQUEST_UPDATE_CODE = 2;

  public EducationFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    educationViewModel = ViewModelProviders.of(getActivity()).get(EducationViewModel.class);
    educationViewModel.getAllEducation().observe(this, new Observer<List<Education>>() {
      @Override
      public void onChanged(List<Education> educations) {
        adapter.submitList(educations);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_education, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_education);
    floatingActionButton = view.findViewById(R.id.fab_add_education);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    // Add education
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        AddEducationDialog dialog = new AddEducationDialog();
        dialog.setTargetFragment(EducationFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Add Education");
      }
    });

    // Swipe to delete education
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
      | ItemTouchHelper.RIGHT ) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Education education = adapter.getItemAt(viewHolder.getAdapterPosition());
        educationViewModel.deleteEducation(education);
      }
    }).attachToRecyclerView(recyclerView);

    // For updating education
    adapter.setOnItemClickListener(new EducationListAdapter.OnItemClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onItemClick(Education education) {
        Bundle bundle = new Bundle();
        bundle.putInt(AddEducationDialog.INTENT_ID, education.getId());
        bundle.putString(AddEducationDialog.INTENT_INSTITUTION, education.getInstitution());
        bundle.putString(AddEducationDialog.INTENT_QUALIFICATION, education.getQualification());
        bundle.putString(AddEducationDialog.INTENT_PERIOD, education.getPeriod());
        bundle.putString(AddEducationDialog.INTENT_DESCRIPTION, education.getDescription());

        AddEducationDialog dialog = new AddEducationDialog();
        dialog.setArguments(bundle);
        dialog.setTargetFragment(EducationFragment.this, DIALOG_REQUEST_UPDATE_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Edit Education");
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      String institution = data.getStringExtra(AddEducationDialog.INTENT_INSTITUTION);
      String qualificationLevel = data.getStringExtra(AddEducationDialog.INTENT_QUALIFICATION);
      String period = data.getStringExtra(AddEducationDialog.INTENT_PERIOD);
      String description = data.getStringExtra(AddEducationDialog.INTENT_DESCRIPTION);
      Education education = new Education(institution, qualificationLevel, period, description);

      if (requestCode == DIALOG_REQUEST_ADD_CODE) {
        educationViewModel.insertEducation(education);
      } else if (requestCode == DIALOG_REQUEST_UPDATE_CODE) {
        int id = data.getIntExtra(AddEducationDialog.INTENT_ID, 0);
        education.setId(id);
        educationViewModel.updateEducation(education);
      }
    }
  }
}
