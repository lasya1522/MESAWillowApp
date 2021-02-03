package com.example.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.app.ui.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    SettingsFragment sf;
    TextView tv_viewAlarmTime;
    private Button button; //Daily quiz button
    public static Calendar c; // public static?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // sf = (SettingsFragment) this.getSupportFragmentManager().findFragmentByTag("fragment_settings");

        tv_viewAlarmTime = findViewById(R.id.tv_viewAlarmTime);
      //  c = Calendar.getInstance();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_trends, R.id.navigation_goals, R.id.navigation_journal, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }

    //this is the website button
    public void browser1(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://suicidepreventionlifeline.org/talk-to-someone-now/"));
        startActivity(browserIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
       // updateTimeText(c);
       // startAlarm(c);
      //  Toast.makeText(this, sf.toString(), Toast.LENGTH_LONG).show();
      //  sf = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_settings);
      //  sf.updateTimeText(c);
      //  sf.startAlarm(c);
    }

    public void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
       // SettingsFragment sf = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_settings);
      // tv_viewAlarmTime.setText(timeText);
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
       // tv_viewAlarmTime.setText("Alarm canceled");
    }

}

