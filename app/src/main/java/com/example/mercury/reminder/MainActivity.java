package com.example.mercury.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private Button btnSave;
    private EditText editTitle;
    private EditText editDate;
    private EditText editTime;
    private EditText editDescription;
    SimpleDateFormat dtTime = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dtDate = new SimpleDateFormat("dd-MM-yyyy");
    SharedPreferences sp;
    Calendar calendar;
    Editor editor;
    boolean validation = false;


    @Override
    protected void onResume() {
        super.onResume();
        if (sp.contains("date") && sp.contains("time") && sp.contains("title")) {
            editTitle.setText(sp.getString("title", ""));
            editDate.setText(sp.getString("date", ""));
            editTime.setText(sp.getString("time", ""));
            editDescription.setText(sp.getString("description", ""));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "onCreate Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnSave = (Button) findViewById(R.id.btnSave);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDate = (EditText) findViewById(R.id.editDate);
        editTime = (EditText) findViewById(R.id.editTime);
        editDescription = (EditText) findViewById(R.id.editDescription);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        sp = getPreferences(MODE_PRIVATE);
        editor = sp.edit();
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "EditDate onClick");
                showTimePickerDialog(v);
            }
        });
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndValidateInformation();


            }
        });

    }


    public void showTimePickerDialog(View v) {
        TimePickerDialog timeDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("TAG", "onTimeSet is beggining");
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        editTime.setText(dtTime.format(calendar.getTime()));

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timeDialog.show();
    }

    public void showDatePickerDialog() {

        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("TAG", "onDateSet is beggining");
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.YEAR, year);
                editDate.setText(dtDate.format((calendar.getTime())));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();

    }


    private void saveAndValidateInformation() {

        if (editDate.length() == 0) {
            editDate.setError("Вы должны указать дату");
            return;
        }

        if (editDescription.length() == 0) {
            editDescription.setError("Вы должны указать приписание");
            return;
        }

        editor.putString("title", editTitle.getText().toString());
        editor.putString("date", editDate.getText().toString());
        editor.putString("time", editTime.getText().toString());
        editor.putString("description", editDescription.getText().toString());
        editor.apply();
        Toast.makeText(getApplicationContext(), "Напоминание сохранено", Toast.LENGTH_SHORT).show();

    }

}
