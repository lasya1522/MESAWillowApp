package com.example.app.ui;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.AlertReceiver;
import com.example.app.DatabaseHelper;
import com.example.app.JournalEntry;
import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.ReminderBroadcast;
import com.example.app.TimePickerFragment;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

//import androidx.lifecycle.ViewModel;

public class SettingsFragment extends Fragment{

    public TextView tv_viewAlarmTime;
    private SettingsViewModel settingsViewModel;
    private Button btn_clearData; // should the variables be private or not????????????
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        btn_clearData = root.findViewById(R.id.btn_clearData);
        databaseHelper = new DatabaseHelper(this.getContext());

        tv_viewAlarmTime = root.findViewById(R.id.tv_viewAlarmTime);
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
        Button button_remind = root.findViewById(R.id.btn_remind);

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

        Button buttonTimePicker = root.findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker"); // should it be a child fragment manager? used to be supportFragmentManager
            }
        });
        Button buttonCancelAlarm = root.findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


        return root;

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tv_viewAlarmTime.setText(timeText);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT) // I don't know what this means... it can't run on old versions, I think
    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        tv_viewAlarmTime.setText("Alarm canceled");
    }

    }


    //compatibility method for lower java versions
  /*  private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Willow";
            String description = "An hour ago you wanted a reminder to take your daily quiz!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyUser", name, importance);
            channel.setDescription(description);
            Context context = this.getContext(); // idk if this is best practice; i created this variable myself
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

   */


