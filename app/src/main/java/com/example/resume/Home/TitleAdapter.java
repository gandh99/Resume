package com.example.resume.Home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleViewHolder> {
  private Activity activity;
  private String title;

  public TitleAdapter(Activity activity, String title) {
    this.activity = activity;
    this.title = title;
  }

  @NonNull
  @Override
  public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity).inflate(R.layout.summary_header, parent, false);
    return new TitleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
    holder.textViewHeader.setText(title);
  }

  @Override
  public int getItemCount() {
    return 1;
  }

  class TitleViewHolder extends RecyclerView.ViewHolder {
    TextView textViewHeader;

    public TitleViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewHeader = itemView.findViewById(R.id.summary_header_textView);
    }
  }
}
