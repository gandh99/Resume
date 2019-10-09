package com.example.resume.Skill;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.resume.R;

import java.time.Year;

public class AddSkillDialog extends DialogFragment implements View.OnClickListener {
  final static String INTENT_ID = "id";
  final static String INTENT_TITLE = "title";
  final static String INTENT_PROFICIENCY = "proficiency";
  final static String INTENT_DESCRIPTION = "description";

  @RequiresApi(api = Build.VERSION_CODES.O)
  public AddSkillDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final EditText editTextTitle, editTextDescription;
    final SeekBar seekBarProficiency;
    Button buttonDone;

    final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_skill, null);
    editTextTitle = view.findViewById(R.id.dialog_skill_title);
    seekBarProficiency = view.findViewById(R.id.dialog_skill_proficiency);
    editTextDescription = view.findViewById(R.id.dialog_skill_description);
    buttonDone = view.findViewById(R.id.dialog_skill_done);

    // Setup seekbar for proficiency
    seekBarProficiency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        TextView textViewProficiencyValue = view.findViewById(R.id.textView_proficiency_value);
        textViewProficiencyValue.setText(String.valueOf(progress));
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });

    // Apply saved state (if applicable)
    final Bundle savedState = getArguments();
    if (savedState != null) {
      String title = savedState.getString(INTENT_TITLE);
      String proficiency = savedState.getString(INTENT_PROFICIENCY);
      String description = savedState.getString(INTENT_DESCRIPTION);

      editTextTitle.setText(title);
      seekBarProficiency.setProgress(Integer.valueOf(proficiency));
      editTextDescription.setText(description);
    }

    // Submit button
    buttonDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String title = editTextTitle.getText().toString();
        String proficiency = String.valueOf(seekBarProficiency.getProgress());
        String description = editTextDescription.getText().toString();

        if (!title.equals("") && !description.equals("")) {
          Intent resultIntent = new Intent();

          if (savedState != null) {
            resultIntent.putExtra(INTENT_ID, savedState.getInt(INTENT_ID));
          }

          resultIntent.putExtra(INTENT_TITLE, title);
          resultIntent.putExtra(INTENT_PROFICIENCY, proficiency);
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

  @Override
  public void onClick(View view) {
  }
}
