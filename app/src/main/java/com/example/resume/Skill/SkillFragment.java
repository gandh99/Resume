package com.example.resume.Skill;


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
public class SkillFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton floatingActionButton;
  private SkillListAdapter adapter = new SkillListAdapter();
  private SkillViewModel skillViewModel;
  private Activity activity;
  final private int DIALOG_REQUEST_ADD_CODE = 1;
  final private int DIALOG_REQUEST_UPDATE_CODE = 2;

  public SkillFragment(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    skillViewModel = ViewModelProviders.of(getActivity()).get(SkillViewModel.class);
    skillViewModel.getAllSkills().observe(this, new Observer<List<Skill>>() {
      @Override
      public void onChanged(List<Skill> skills) {
        adapter.submitList(skills);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_skill, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_skill);
    floatingActionButton = view.findViewById(R.id.fab_add_skill);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);

    // Add skill
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        AddSkillDialog dialog = new AddSkillDialog();
        dialog.setTargetFragment(SkillFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Add Skill");
      }
    });

    // Swipe to delete skill
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
      | ItemTouchHelper.RIGHT ) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Skill skill = adapter.getItemAt(viewHolder.getAdapterPosition());
        skillViewModel.deleteSkill(skill);
      }
    }).attachToRecyclerView(recyclerView);

    // For updating skill
    adapter.setOnItemClickListener(new SkillListAdapter.OnItemClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onItemClick(Skill skill) {
        Bundle bundle = new Bundle();
        bundle.putInt(AddSkillDialog.INTENT_ID, skill.getId());
        bundle.putString(AddSkillDialog.INTENT_TITLE, skill.getTitle());
        bundle.putString(AddSkillDialog.INTENT_PROFICIENCY, skill.getProficiency());
        bundle.putString(AddSkillDialog.INTENT_DESCRIPTION, skill.getDescription());

        AddSkillDialog dialog = new AddSkillDialog();
        dialog.setArguments(bundle);
        dialog.setTargetFragment(SkillFragment.this, DIALOG_REQUEST_UPDATE_CODE);
        dialog.show(((MainActivity) activity).getSupportFragmentManager(), "Edit Skill");
      }
    });

    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      String title = data.getStringExtra(AddSkillDialog.INTENT_TITLE);
      String proficiency = data.getStringExtra(AddSkillDialog.INTENT_PROFICIENCY);
      String description = data.getStringExtra(AddSkillDialog.INTENT_DESCRIPTION);
      Skill skill = new Skill(title, proficiency, description);

      if (requestCode == DIALOG_REQUEST_ADD_CODE) {
        skillViewModel.insertSkill(skill);
      } else if (requestCode == DIALOG_REQUEST_UPDATE_CODE) {
        int id = data.getIntExtra(AddSkillDialog.INTENT_ID, 0);
        skill.setId(id);
        skillViewModel.updateSkill(skill);
      }
    }
  }
}
