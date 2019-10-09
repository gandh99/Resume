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

import java.util.List;

public class EducationListAdapter extends ListAdapter<Education, EducationListAdapter.EducationViewHolder> {
  private OnItemClickListener onItemClickListener;

  public EducationListAdapter() {
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
        && oldItem.getPeriod().equals(newItem.getPeriod())
        && oldItem.getQualification().equals(newItem.getQualification())
        && oldItem.getDescription().equals(newItem.getDescription()));
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
    holder.textViewQualificationLevel.setText(education.getQualification());
    holder.textViewInstitution.setText(education.getInstitution());
    holder.textViewPeriod.setText(education.getPeriod());
    holder.textViewDescription.setText(education.getDescription());
  }

  public Education getItemAt(int position) {
    return getItem(position);
  }

  class EducationViewHolder extends RecyclerView.ViewHolder {
    TextView textViewQualificationLevel, textViewInstitution, textViewPeriod, textViewDescription;

    public EducationViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewQualificationLevel = itemView.findViewById(R.id.list_item_education_qualification);
      textViewInstitution = itemView.findViewById(R.id.list_item_education_institution);
      textViewPeriod = itemView.findViewById(R.id.list_item_education_period);
      textViewDescription = itemView.findViewById(R.id.list_item_education_description);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int position = getAdapterPosition();

          if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
            onItemClickListener.onItemClick(getItem(position));
          }
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onItemClick(Education education);
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
