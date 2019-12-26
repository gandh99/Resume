package com.example.resume.Achievement;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.resume.R;

import java.time.Year;

public class AddAchievementDialog extends DialogFragment {
  final static String INTENT_ID = "id";
  final static String INTENT_TITLE = "title";
  final static String INTENT_YEAR = "year";
  final static String INTENT_DESCRIPTION = "description";

  final private int NUM_OF_YEARS = 100;
  private String[] years = new String[NUM_OF_YEARS];

  @RequiresApi(api = Build.VERSION_CODES.O)
  public AddAchievementDialog() {
    for (int i = 0; i < NUM_OF_YEARS; i++) {
      years[i] = String.valueOf(Year.now().getValue() - i);
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final EditText editTextTitle, editTextYear, editTextDescription;
    Button buttonDone;

    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_achievement, null);
    editTextTitle = view.findViewById(R.id.dialog_achievement_title);
    editTextYear = view.findViewById(R.id.dialog_achievement_year);
    editTextDescription = view.findViewById(R.id.dialog_achievement_description);
    buttonDone = view.findViewById(R.id.dialog_achievement_done);

    // Apply saved state (if applicable)
    final Bundle savedState = getArguments();
    if (savedState != null) {
      String title = savedState.getString(INTENT_TITLE);
      String year = savedState.getString(INTENT_YEAR);
      String description = savedState.getString(INTENT_DESCRIPTION);

      editTextTitle.setText(title);
      editTextYear.setText(year);
      editTextDescription.setText(description);
    }

    // Submit button
    buttonDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String title = editTextTitle.getText().toString();
        String year = editTextYear.getText().toString();
        String description = editTextDescription.getText().toString();

        if (!title.equals("") && !year.equals("") && !description.equals("")) {
          Intent resultIntent = new Intent();

          if (savedState != null) {
            resultIntent.putExtra(INTENT_ID, savedState.getInt(INTENT_ID));
          }

          resultIntent.putExtra(INTENT_TITLE, title);
          resultIntent.putExtra(INTENT_YEAR, year);
          resultIntent.putExtra(INTENT_DESCRIPTION, description);

          getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, resultIntent);
          dismiss();
        }
      }
    });

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }
}
