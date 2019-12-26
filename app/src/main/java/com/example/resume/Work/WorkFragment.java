package com.example.resume.Work;


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

import com.example.resume.Education.AddEducationDialog;
import com.example.resume.Education.Education;
import com.example.resume.Education.EducationFragment;
import com.example.resume.MainActivity;
import com.example.resume.R;
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
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        AddWorkDialog dialog = new AddWorkDialog();
        dialog.setTargetFragment(WorkFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Add Work");
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
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onItemClick(Work work) {
        Bundle bundle = new Bundle();
        bundle.putInt(AddWorkDialog.INTENT_ID, work.getId());
        bundle.putString(AddWorkDialog.INTENT_COMPANY, work.getCompany());
        bundle.putString(AddWorkDialog.INTENT_POSITION, work.getPosition());
        bundle.putString(AddWorkDialog.INTENT_PERIOD, work.getPeriod());
        bundle.putString(AddWorkDialog.INTENT_DESCRIPTION, work.getDescription());

        AddWorkDialog dialog = new AddWorkDialog();
        dialog.setArguments(bundle);
        dialog.setTargetFragment(WorkFragment.this, DIALOG_REQUEST_UPDATE_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Edit Work");
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      String company = data.getStringExtra(AddWorkDialog.INTENT_COMPANY);
      String position = data.getStringExtra(AddWorkDialog.INTENT_POSITION);
      String period = data.getStringExtra(AddWorkDialog.INTENT_PERIOD);
      String description = data.getStringExtra(AddWorkDialog.INTENT_DESCRIPTION);
      Work work = new Work(company, position, period, description);

      if (requestCode == DIALOG_REQUEST_ADD_CODE) {
        workViewModel.insertWork(work);
      } else if (requestCode == DIALOG_REQUEST_UPDATE_CODE) {
        int id = data.getIntExtra(AddWorkDialog.INTENT_ID, 0);
        work.setId(id);
        workViewModel.updateWork(work);
      }
    }
  }

}
