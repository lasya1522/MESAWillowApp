package com.example.app.ui.home_tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.DailyQuiz;
import com.example.app.DailyQuizActivity;
import com.example.app.DatabaseHelper;
import com.example.app.PastQuizzesActivity;
import com.example.app.R;
import com.example.app.SettingsActivity;

import java.util.Calendar;

//import com.example.app.DailyQuizActivity;
//import com.example.app.ui.DailyQuizzesActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button button; //Daily quiz button

    Button btn_viewPastQuizzes; //Previous quiz button
    Button btn_dailyQuiz;
    Button btn_settings;

    DatabaseHelper databaseHelper;


    ImageView lock;

    CalendarView cv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btn_dailyQuiz = root.findViewById(R.id.viewQuiz);
        lock = root.findViewById(R.id.lock);
        btn_settings = root.findViewById(R.id.btn_settings);


        databaseHelper = new DatabaseHelper(this.getContext());
        if (databaseHelper.getDailyQuiz(getTodayDate()).getDate().equals("")){
            lock.setImageResource(R.drawable.ic_unlocked_foreground);
        } else {
            lock.setImageResource(R.drawable.ic_locked_foreground);
        }

        btn_dailyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DailyQuizActivity.class);
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        //past quizzes btn
        btn_viewPastQuizzes = root.findViewById(R.id.btn_viewPastQuizzes);
        btn_viewPastQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PastQuizzesActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    // these methods are in a lot of files-- can we make them public and then use them everywhere?

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int year = cal.get(Calendar.YEAR);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return (month + "-" + dayOfMonth + "-" + year);
    }
}