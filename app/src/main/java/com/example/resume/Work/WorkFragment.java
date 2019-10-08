package com.example.resume.Work;


import android.app.Activity;
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

import com.example.resume.Education.Education;
import com.example.resume.Education.EducationListAdapter;
import com.example.resume.R;
import com.example.resume.Education.EducationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private WorkListAdapter adapter = new WorkListAdapter();
  private WorkViewModel workViewModel;
  private Activity activity;
  final private int DIALOG_REQUEST_ADD_CODE = 1;
  final private int DIALOG_REQUEST_UPDATE_CODE = 2;

  public WorkFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    workViewModel = ViewModelProviders.of(getActivity()).get(WorkViewModel.class);
    workViewModel.getAllWork().observe(this, new Observer<List<Work>>() {
      @Override
      public void onChanged(List<Work> works) {
        adapter.submitList(works);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_work, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_work);
    floatingActionButton = view.findViewById(R.id.fab_add_work);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    recyclerView.setAdapter(adapter);

    // Add work
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    // Swipe to delete work
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
      | ItemTouchHelper.RIGHT ) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Work work = adapter.getItemAt(viewHolder.getAdapterPosition());
        workViewModel.deleteWork(work);
      }
    }).attachToRecyclerView(recyclerView);

    // Update work
    adapter.setOnItemClickListener(new WorkListAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(Work work) {

      }
    });

    return view;
  }

}
