package com.example.resume.Skill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.Education.Education;
import com.example.resume.R;

public class SkillListAdapter extends ListAdapter<Skill, SkillListAdapter.SkillViewHolder> {
  private OnItemClickListener onItemClickListener;

  public SkillListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Skill> DIFF_CALLBACK = new DiffUtil.ItemCallback<Skill>() {
    @Override
    public boolean areItemsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
      return (oldItem.getTitle().equals(newItem.getTitle())
        && oldItem.getProficiency().equals(newItem.getProficiency())
        && oldItem.getDescription().equals(newItem.getDescription()));
    }
  };

  @NonNull
  @Override
  public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.list_item_skill, parent, false);

    return new SkillViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
    Skill skill = getItem(position);
    holder.textViewTitle.setText(skill.getTitle());
    holder.textViewProficiency.setText(skill.getProficiency());
    holder.textViewDescription.setText(skill.getDescription());
  }

  public Skill getItemAt(int position) {
    return getItem(position);
  }

  class SkillViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewProficiency, textViewDescription;

    public SkillViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewTitle = itemView.findViewById(R.id.list_item_skill_title);
      textViewProficiency = itemView.findViewById(R.id.list_item_skill_proficiency);
      textViewDescription = itemView.findViewById(R.id.list_item_skill_description);

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
    void onItemClick(Skill skill);
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
