package com.example.resume.Work;

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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.resume.R;

import java.time.Year;

public class AddWorkDialog extends DialogFragment {
  final static String INTENT_ID = "id";
  final static String INTENT_COMPANY = "company";
  final static String INTENT_POSITION = "position";
  final static String INTENT_PERIOD = "period";
  final static String INTENT_DESCRIPTION = "description";

  final private int NUM_OF_YEARS = 100;
  private String[] years = new String[NUM_OF_YEARS];

  @RequiresApi(api = Build.VERSION_CODES.O)
  public AddWorkDialog() {
    for (int i = 0; i < NUM_OF_YEARS; i++) {
      years[i] = String.valueOf(Year.now().getValue() - i);
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final EditText editTextCompany, editTextPosition, editTextDescription;
    final Spinner spinnerStartPeriodMonth, spinnerStartPeriodYear,
      spinnerEndPeriodMonth, spinnerEndPeriodYear;
    Button buttonDone;

    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_work, null);
    editTextCompany = view.findViewById(R.id.dialog_work_company);
    editTextPosition = view.findViewById(R.id.dialog_work_position);
    editTextDescription = view.findViewById(R.id.dialog_work_description);
    spinnerStartPeriodMonth = view.findViewById(R.id.dialog_work_startPeriod_month);
    spinnerStartPeriodYear = view.findViewById(R.id.dialog_work_startPeriod_year);
    spinnerEndPeriodMonth = view.findViewById(R.id.dialog_work_endPeriod_month);
    spinnerEndPeriodYear = view.findViewById(R.id.dialog_work_endPeriod_year);
    buttonDone = view.findViewById(R.id.dialog_work_done);

    // Setup spinners
    setupSpinner(spinnerStartPeriodMonth, R.array.months);
    setupSpinner(spinnerStartPeriodYear, years);
    setupSpinner(spinnerEndPeriodMonth, R.array.months);
    setupSpinner(spinnerEndPeriodYear, years);

    // Apply saved state (if applicable)
    final Bundle savedState = getArguments();
    if (savedState != null) {
      String company = savedState.getString(INTENT_COMPANY);
      String position = savedState.getString(INTENT_POSITION);
      String description = savedState.getString(INTENT_DESCRIPTION);
      String[] period = savedState.getString(INTENT_PERIOD).split(" ");

      editTextCompany.setText(company);
      editTextPosition.setText(position);
      editTextDescription.setText(description);
      spinnerStartPeriodMonth.setSelection(monthToIndex(period[0]));
      spinnerStartPeriodYear.setSelection(Integer.parseInt(years[0]) - Integer.parseInt(period[1]));
      spinnerEndPeriodMonth.setSelection(monthToIndex(period[3]));
      spinnerEndPeriodYear.setSelection(Integer.parseInt(years[0]) - Integer.parseInt(period[4]));
    }

    // Submit button
    buttonDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String company = editTextCompany.getText().toString();
        String position = editTextPosition.getText().toString();
        String startPeriodMonth = spinnerStartPeriodMonth.getSelectedItem().toString();
        String startPeriodYear = spinnerStartPeriodYear.getSelectedItem().toString();
        String endPeriodMonth = spinnerEndPeriodMonth.getSelectedItem().toString();
        String endPeriodYear = spinnerEndPeriodYear.getSelectedItem().toString();
        String period = startPeriodMonth + " " + startPeriodYear + " - " + endPeriodMonth + " " + endPeriodYear;
        String description = editTextDescription.getText().toString();

        if (!company.equals("") && !description.equals("")) {
          Intent resultIntent = new Intent();

          if (savedState != null) {
            resultIntent.putExtra(INTENT_ID, savedState.getInt(INTENT_ID));
          }

          resultIntent.putExtra(INTENT_COMPANY, company);
          resultIntent.putExtra(INTENT_POSITION, position);
          resultIntent.putExtra(INTENT_PERIOD, period);
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

  private int monthToIndex(String selectedMonth) {
    String[] months = getResources().getStringArray(R.array.months);

    for (int i = 0; i < months.length; i++) {
      if (months[i].equals(selectedMonth)) {
        return i;
      }
    }

    return 0;
  }

  private void setupSpinner(Spinner spinner, int resID) {
    ArrayAdapter<CharSequence> adapter =
      ArrayAdapter.createFromResource(getContext(),
        resID, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }

  private void setupSpinner(Spinner spinner, String[] items) {
    ArrayAdapter<String> adapter =
      new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }
}
