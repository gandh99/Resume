package com.example.resume.Education;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resume.AddEducationDialog;
import com.example.resume.MainActivity;
import com.example.resume.R;
import com.example.resume.ResumeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EducationFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private EducationListAdapter adapter = new EducationListAdapter();
  private ResumeViewModel resumeViewModel;
  private Activity activity;
  private int DIALOG_REQUEST_CODE = 1;

  public EducationFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    resumeViewModel = ViewModelProviders.of(getActivity()).get(ResumeViewModel.class);
    resumeViewModel.getAllEducation().observe(this, new Observer<List<Education>>() {
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

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    // Add education
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AddEducationDialog dialog = new AddEducationDialog();
        dialog.setTargetFragment(EducationFragment.this, DIALOG_REQUEST_CODE);
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
        resumeViewModel.deleteEducation(education);
      }
    }).attachToRecyclerView(recyclerView);

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == DIALOG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      String institution = data.getStringExtra("institution");
      String period = data.getStringExtra("period");
      Education education = new Education(institution, period);
      resumeViewModel.insertEducation(education);
    }
  }
}
