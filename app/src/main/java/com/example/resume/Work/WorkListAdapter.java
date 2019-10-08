package com.example.resume.Work;

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
      return true; //TODO
    }
  };

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
  }

  class WorkViewHolder extends RecyclerView.ViewHolder {
    TextView textView; //TODO

    public WorkViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  public interface OnItemClickListener {
    void onItemClick(Work work);
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
