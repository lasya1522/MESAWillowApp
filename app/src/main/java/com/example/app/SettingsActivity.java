package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.app.ui.SettingsViewModel;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public TextView tv_viewAlarmTime;
    private SettingsViewModel settingsViewModel;
    private Button btn_clearData; // should the variables be private or not????????????
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        btn_clearData = findViewById(R.id.btn_clearData2);
        databaseHelper = new DatabaseHelper(this);

        tv_viewAlarmTime = findViewById(R.id.tv_viewAlarmTime2);
        if (MainActivity.c != null) {
            updateTimeText(MainActivity.c);
            startAlarm(MainActivity.c);
        }

        btn_clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteAll();
            }
        });

        //-------------------------------------------
        //Beginning of notification section
        //-------------------------------------------
        //new class goes with this - ReminderBroadcast.java
        //pretty sure that class is fine, its this place that has issues
        //-------------------------------------------
        // reminder button
        // createNotificationChannel();

        //find button to use. btn_remind is on the settings page
        Button button_remind = findViewById(R.id.btn_remind2);

        //button can be found on settings page - fragment_settings.xml


        /*button_remind.setOnClickListener(v -> {
            //toast says that user clicked the button.
            Toast.makeText(this.getContext(), "Reminder in one hour set!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getContext(), ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 0, intent, 0);

            //get alarm manager
            Context context = this.getContext(); // idk if this is best practice; i created this variable myself
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();
            // This is where user defines time. Right now its at 10 seconds for debugging reasons.
            //We could later have a user input field and take that information and use it for custom time here.
            long tenSecondsInMillis = 1000+5;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 19);

          // With setInexactRepeating(), you have to use one of the AlarmManager interval
           // constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        });

         */

        Button buttonTimePicker = findViewById(R.id.button_timepicker2);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker"); // should it be a child fragment manager? used to be supportFragmentManager
            }
        });
        Button buttonCancelAlarm = findViewById(R.id.button_cancel2);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tv_viewAlarmTime.setText(timeText);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT) // I don't know what this means... it can't run on old versions, I think
    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        tv_viewAlarmTime.setText("Alarm canceled");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT) // what does this mean
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }
}
