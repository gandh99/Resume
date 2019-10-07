package com.example.resume;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    final EditText editTextInstitution;
    final EditText editTextPeriod;
    Button buttonDone;

    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_education, null);
    editTextInstitution = view.findViewById(R.id.dialog_education_institution);
    editTextPeriod = view.findViewById(R.id.dialog_education_period);
    buttonDone = view.findViewById(R.id.dialog_education_done);

    buttonDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String institution = editTextInstitution.getText().toString();
        String period = editTextPeriod.getText().toString();

        if (!institution.equals("") && !period.equals("")) {
          Intent resultIntent = new Intent();
          resultIntent.putExtra("institution", institution);
          resultIntent.putExtra("period", period);

          getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, resultIntent);
          dismiss();
        }
      }
    });

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  @Override
  public void onClick(View view) {

  }
}
