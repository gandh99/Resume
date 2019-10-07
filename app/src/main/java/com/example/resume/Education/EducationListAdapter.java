package com.example.resume.Education;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;

public class EducationListAdapter extends ListAdapter<Education, EducationListAdapter.EducationViewHolder> {

  protected EducationListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Education> DIFF_CALLBACK = new DiffUtil.ItemCallback<Education>() {
    @Override
    public boolean areItemsTheSame(@NonNull Education oldItem, @NonNull Education newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Education oldItem, @NonNull Education newItem) {
      return (oldItem.getInstitution().equals(newItem.getInstitution())
        && oldItem.getPeriod().equals(newItem.getPeriod()));
    }
  };

  @NonNull
  @Override
  public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.list_item_education, parent, false);

    return new EducationViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
    Education education = getItem(position);
    holder.textViewInstitution.setText(education.getInstitution());
    holder.textViewPeriod.setText(education.getPeriod());
  }

  class EducationViewHolder extends RecyclerView.ViewHolder {
    TextView textViewInstitution;
    TextView textViewPeriod;

    public EducationViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewInstitution = itemView.findViewById(R.id.list_item_education_institution);
      textViewPeriod = itemView.findViewById(R.id.list_item_education_period);
    }
  }
}
