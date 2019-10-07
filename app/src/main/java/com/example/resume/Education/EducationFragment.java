package com.example.resume.Education;


import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resume.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class EducationFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private EducationListAdapter adapter;

  public EducationFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_education, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_education);
    floatingActionButton = view.findViewById(R.id.fab_add_education);

    adapter = new EducationListAdapter();
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    return view;
  }

}
