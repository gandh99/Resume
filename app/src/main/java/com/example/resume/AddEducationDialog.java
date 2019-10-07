package com.example.resume;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddEducationDialog extends DialogFragment implements View.OnClickListener {

  public AddEducationDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_education, null);

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  @Override
  public void onClick(View view) {

  }
}
