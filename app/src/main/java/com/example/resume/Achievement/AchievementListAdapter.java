package com.example.resume.Achievement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;


public class AchievementListAdapter extends ListAdapter<Achievement, AchievementListAdapter.AchievementViewHolder> {
  private OnItemClickListener onItemClickListener;

  public AchievementListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Achievement> DIFF_CALLBACK = new DiffUtil.ItemCallback<Achievement>() {
    @Override
    public boolean areItemsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Achievement oldItem, @NonNull Achievement newItem) {
      return (oldItem.getTitle().equals(newItem.getTitle())
        && oldItem.getYear().equals(newItem.getYear())
        && oldItem.getDescription().equals(newItem.getDescription()));
    }
  };

  @NonNull
  @Override
  public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_achievement, parent, false);

    return new AchievementViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
    Achievement achievement = getItem(position);
    holder.textViewTitle.setText(achievement.getTitle());
    holder.textViewYear.setText(achievement.getYear());
    holder.textViewDescrption.setText(achievement.getDescription());
  }

  public Achievement getItemAt(int position) {
    return getItem(position);
  }

  class AchievementViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewYear, textViewDescrption;

    public AchievementViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewTitle = itemView.findViewById(R.id.list_item_achievement_title);
      textViewYear = itemView.findViewById(R.id.list_item_achievement_year);
      textViewDescrption = itemView.findViewById(R.id.list_item_achievement_description);

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
    void onItemClick(Achievement achievement);
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
