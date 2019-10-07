package com.example.resume.Education;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

  public EducationFragment() {
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

    return view;
  }

}
