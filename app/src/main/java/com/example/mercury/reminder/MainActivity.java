package com.example.mercury.reminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
    private long total;
    private Button btnSave;
    private EditText editTitle;
    private EditText editDate;
    private EditText editTime;
    private EditText editDescription;
    private SimpleDateFormat dtTime = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dtDate = new SimpleDateFormat("dd-MM-yyyy");
    private SharedPreferences sp;
    private Calendar calendar;
    private Editor editor;
    private AlarmManager alarmManager;
    private ToServiceReceiver receiver;
    @Override
    protected void onResume() {
        super.onResume();
        if (sp.contains("date") && sp.contains("time") && sp.contains("title")) {
            editTitle.setText(sp.getString("title", ""));
            editDate.setText(sp.getString("date", ""));
            editTime.setText(sp.getString("time", ""));
            editDescription.setText(sp.getString("description", ""));
        }
        receiver = new ToServiceReceiver();
        this.registerReceiver(receiver,)
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
        //ждем, когда пользователь вызовет диалог с выбором времени
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "EditDate onClick");
                showTimePickerDialog(v);
            }
        });
        //ждем, когда пользователь вызовет диалог с выбором даты
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
        calendar.getTimeInMillis();
        dateDialog.show();

    }

    private void saveAndValidateInformation() {
        //пользователь сохраняет введенные данные
        //производим валидацию на отсутствие даты\времени

        if (editDate.length() == 0) {
            editDate.setError("Вы должны указать дату");
            return;
        }

        if (editTime.length() == 0) {
            editTime.setError("Вы должны указать время");
            return;
        }

        if(editTitle.length() == 0){
            editTitle.setError("Вы должны ввести заголовок напоминания");
            return;
        }
        //валидация пройдена, сохраняем  значения
        editor.putString("title", editTitle.getText().toString());
        editor.putString("date", editDate.getText().toString());
        editor.putString("time", editTime.getText().toString());
        editor.putString("description", editDescription.getText().toString());
        editor.apply();
        total = calendar.getTime().getTime();
        Toast.makeText(getApplicationContext(), "Напоминание сохранено", Toast.LENGTH_SHORT).show();
    //   sendToNotificate();
    }


    private void restartNotify() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ToServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );

        alarmManager.cancel(pendingIntent);
// Устанавливаем разовое напоминание
        alarmManager.set(AlarmManager.RTC_WAKEUP, total, pendingIntent);
    }


  /*  private void sendToNotificate(){
        Intent dataIntent = new Intent("test");
        dataIntent.putExtra("title",sp.getString("title", ""));
        dataIntent.putExtra("date", sp.getString("date",""));
        dataIntent.putExtra("time",sp.getString("time",""));
        dataIntent.putExtra("description",sp.getString("description",""));

    }*/

}
