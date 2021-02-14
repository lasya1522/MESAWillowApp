package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPastQuizActivity extends AppCompatActivity {
    TextView tv_date;
    TextView tv_mood;
    TextView tv_sleepTime;
    TextView tv_sleepRating;
    TextView tv_productiveTime;
    TextView tv_relaxTime;
    TextView tv_exerciseTime;
    TextView tv_stressLevel;
    TextView tv_stressors;
    TextView tv_other;

    CardView card_mood;
    CardView card_sleep;
    CardView card_productive;
    CardView card_relax;
    CardView card_exercise;
    CardView card_stress;
    CardView card_other;

    List<DailyQuiz> list;
    DailyQuiz dailyQuiz;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_quiz);

        tv_date = findViewById(R.id.tv_date);
        tv_mood = findViewById(R.id.tv_mood);
        tv_sleepTime = findViewById(R.id.tv_sleepTime);
        tv_sleepRating = findViewById(R.id.tv_sleepRating);
        tv_productiveTime = findViewById(R.id.tv_productiveTime);
        tv_relaxTime = findViewById(R.id.tv_relaxTime);
        tv_exerciseTime = findViewById(R.id.tv_exerciseTime);
        tv_stressLevel = findViewById(R.id.tv_stressLevel);
        tv_stressors = findViewById(R.id.tv_stressors);
        tv_other = findViewById(R.id.tv_other);

        card_mood = findViewById(R.id.past_quiz_mood_card);
        card_sleep = findViewById(R.id.past_quiz_sleep_card);
        card_relax = findViewById(R.id.past_quiz_relax_card);
        card_productive = findViewById(R.id.past_quiz_productive_card);
        card_exercise = findViewById(R.id.past_quiz_exercise_card);
        card_stress = findViewById(R.id.past_quiz_stress_card);
        card_other = findViewById(R.id.past_quiz_other_card);

        Intent intent = getIntent();
        String passedDate = intent.getStringExtra("date");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        dailyQuiz = databaseHelper.getDailyQuiz(passedDate);
        tv_date.setText("Quiz results for " + passedDate);

        if (dailyQuiz.getDate().equals("")){
            tv_mood.setText("It looks like you didn't fill out a daily quiz on " + passedDate);
            card_relax.setVisibility(View.GONE);
            card_sleep.setVisibility(View.GONE);
            card_productive.setVisibility(View.GONE);
            card_stress.setVisibility(View.GONE);
            card_other.setVisibility(View.GONE);
            card_exercise.setVisibility(View.GONE);
        } else {
            tv_mood.setText("Mood: " + dailyQuiz.getMood());
            tv_sleepTime.setText("Hours spent sleeping: " + dailyQuiz.getSleepTime().toString());
            tv_sleepRating.setText("Sleep rating: " + dailyQuiz.getSleepRating());
            tv_productiveTime.setText("Hours spent being productive: " + dailyQuiz.getProductiveTime().toString());
            tv_relaxTime.setText("Hours spent relaxing: " + dailyQuiz.getRelaxTime().toString());
            tv_exerciseTime.setText("Hours spend exercising: " + dailyQuiz.getExerciseTime().toString());
            tv_stressLevel.setText("Stress levels: " + dailyQuiz.getStressLevel());
            if (dailyQuiz.getStressors().equals("")){
                tv_stressors.setText("Stressors: N/A");
            } else {
                tv_stressors.setText("Stressors: " + dailyQuiz.getStressors());
            }
            if (dailyQuiz.getOther().equals("")){
                tv_other.setText("Other: N/A");
            } else {
                tv_other.setText("Other notes: " + dailyQuiz.getOther());
            }
        }


    }

}
