package com.example.resume.Work;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;

public class WorkListAdapter extends ListAdapter<Work, WorkListAdapter.WorkViewHolder> {
  private OnItemClickListener onItemClickListener;

  protected WorkListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Work> DIFF_CALLBACK = new DiffUtil.ItemCallback<Work>() {
    @Override
    public boolean areItemsTheSame(@NonNull Work oldItem, @NonNull Work newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Work oldItem, @NonNull Work newItem) {
      return (oldItem.getCompany().equals(newItem.getCompany())
        && oldItem.getPosition().equals(newItem.getPosition())
        && oldItem.getPeriod().equals(newItem.getPeriod())
        && oldItem.getDescription().equals(newItem.getDescription()));
    }
  };

  public Work getItemAt(int position) {
    return getItem(position);
  }

  @NonNull
  @Override
  public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_work, parent, false);

    return new WorkViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
    Work work = getItem(position);
    holder.textViewCompany.setText(work.getCompany());
    holder.textViewPosition.setText(work.getPosition());
    holder.textViewPeriod.setText(work.getPeriod());
    holder.textViewDescription.setText(work.getDescription());
  }

  class WorkViewHolder extends RecyclerView.ViewHolder {
    TextView textViewCompany, textViewPosition, textViewPeriod, textViewDescription;

    public WorkViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewCompany = itemView.findViewById(R.id.list_item_work_company);
      textViewPosition = itemView.findViewById(R.id.list_item_work_position);
      textViewPeriod = itemView.findViewById(R.id.list_item_work_period);
      textViewDescription = itemView.findViewById(R.id.list_item_work_description);

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
    void onItemClick(Work work);
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
