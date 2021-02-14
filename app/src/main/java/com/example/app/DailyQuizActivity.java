package com.example.app;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class DailyQuizActivity extends AppCompatActivity {

    //ARE THESE SUPPOSED TO BE PRIVATE OR JUST NOTHING??????? SEE PASTQUIZZESACTIVITY

    //WE NEED TO FIGURE OUT HOW TO DEAL WITH POSSIBLE DECIMAL INPUT

    //need more consistency with whether we're using instance vars or regular vars that are inside the onCreate()
    //waht is the difference? rn, it's not so consistent

    EditText et_sleepRating;
    EditText et_sleepDuration;

    Button btn_submit;

    Button btn_mood1;
    Button btn_mood2;
    Button btn_mood3;
    Button btn_mood4;

    EditText et_sleepTime;

    Button btn_sleepRating1;
    Button btn_sleepRating2;
    Button btn_sleepRating3;

    EditText et_productiveTime;
    EditText et_relaxTime;
    EditText et_exerciseTime;

    Button btn_stressLevel1;
    Button btn_stressLevel2;
    Button btn_stressLevel3;
    Button btn_stressLevel4;

    EditText et_stressors;
    EditText et_other;


    private Date date;
    private String mood;
    private Integer sleepTime;
    private String sleepRating;
    private Integer productiveTime;
    private Integer relaxTime;
    private Integer exerciseTime;
    private String stressLevel;
    private String stressors;
    private String other;//is this really the best way to keep track of the mood ? // may change to MC buttons

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);

        //Underline quiz title
       // TextView quizTitle = (TextView) findViewById(R.id.dailyQuizTitle);
       // quizTitle.setPaintFlags(quizTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

        btn_mood1 = findViewById(R.id.btn_mood1);
        btn_mood2 = findViewById(R.id.btn_mood2);
        btn_mood3 = findViewById(R.id.btn_mood3);

        et_sleepTime = findViewById(R.id.et_sleepTime);

        btn_sleepRating1 = findViewById(R.id.btn_sleepRating1);
        btn_sleepRating2 = findViewById(R.id.btn_sleepRating2);
        btn_sleepRating3 = findViewById(R.id.btn_sleepRating3);

        et_productiveTime = findViewById(R.id.et_productiveTime);
        et_relaxTime = findViewById(R.id.et_relaxTime);
        et_exerciseTime = findViewById(R.id.et_exerciseTime);

        btn_stressLevel1 = findViewById(R.id.btn_stressLevel1);
        btn_stressLevel2 = findViewById(R.id.btn_stressLevel2);
        btn_stressLevel3 = findViewById(R.id.btn_stressLevel3);

        et_stressors = findViewById(R.id.et_stressors);
        et_other = findViewById(R.id.et_other);

        btn_submit = findViewById(R.id.btn_submit);

        stressors = "";
        other = "";

        databaseHelper = new DatabaseHelper(DailyQuizActivity.this);

      btn_submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              DailyQuiz dailyQuiz;
              //need some way to test whether all necessary fields were filled in with a valid number. initialize int vars to -1? check before try?

              try {

                  //to make it consistent, should I use all String.valueOf() or all .toString()?? or is it ok how it is?
                  String date = getTodayDate();
                  //surely there is a nicer way to code this?????
                  if ((et_sleepTime.getText().toString().trim().equals("")) || ((et_productiveTime.getText().toString().trim().equals(""))) || (et_relaxTime.getText().toString().trim().equals("") || (et_exerciseTime.getText().toString().trim().equals("")))) {
                      Toast.makeText(DailyQuizActivity.this, "required fields left empty", Toast.LENGTH_SHORT).show();

                  } else if ((mood == null) || (date == null) || (sleepRating == null) || (stressLevel == null)) {
                      Toast.makeText(DailyQuizActivity.this, "mood, date, sleepRating, or stressLevel is null", Toast.LENGTH_SHORT).show();

                  } else if ((Integer.parseInt(String.valueOf(et_sleepTime.getText())) > 24) || (Integer.parseInt(String.valueOf(et_sleepTime.getText())) < 0)) {
                      Toast.makeText(DailyQuizActivity.this, "number not allowed", Toast.LENGTH_SHORT).show();

                  } else if ((Integer.parseInt(String.valueOf(et_productiveTime.getText())) > 24) || (Integer.parseInt(String.valueOf(et_productiveTime.getText())) < 0)) {
                      Toast.makeText(DailyQuizActivity.this, "number not allowed", Toast.LENGTH_SHORT).show();

                  } else if ((Integer.parseInt(String.valueOf(et_relaxTime.getText())) > 24) || (Integer.parseInt(String.valueOf(et_relaxTime.getText())) < 0)) {
                      Toast.makeText(DailyQuizActivity.this, "number not allowed", Toast.LENGTH_SHORT).show();

                  } else if ((Integer.parseInt(String.valueOf(et_exerciseTime.getText())) > 24) || (Integer.parseInt(String.valueOf(et_exerciseTime.getText())) < 0)) {
                      Toast.makeText(DailyQuizActivity.this, "number not allowed", Toast.LENGTH_SHORT).show();

                  } else {
                      if (!(et_stressors.getText().toString().trim().equals(""))) {
                          stressors = et_stressors.getText().toString();
                      }
                      if (!(et_other.getText().toString().trim().equals(""))) {
                          other = et_other.getText().toString();
                      }

                      sleepTime = Integer.parseInt(String.valueOf(et_sleepTime.getText()));
                      productiveTime = Integer.parseInt(String.valueOf(et_productiveTime.getText()));
                      relaxTime = Integer.parseInt(String.valueOf(et_relaxTime.getText()));
                      exerciseTime = Integer.parseInt(String.valueOf(et_exerciseTime.getText()));

                      //technically, will we need to change this code because I basically copied it off a tutorial?
                      dailyQuiz = new DailyQuiz(date, mood, sleepTime, sleepRating, productiveTime, relaxTime, exerciseTime, stressLevel, stressors, other);
                      Toast.makeText(DailyQuizActivity.this, dailyQuiz.toString(), Toast.LENGTH_SHORT).show();
                      databaseHelper = new DatabaseHelper(DailyQuizActivity.this);
                      boolean success = databaseHelper.addDailyQuiz(dailyQuiz);
                      Toast.makeText(DailyQuizActivity.this, "success " + success, Toast.LENGTH_SHORT).show();
                  }
              } catch (Exception e) {
                  Toast.makeText(DailyQuizActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

              }
          }
        });

        btn_mood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mood = "Good";
               // Toast.makeText(MorningQuizActivity.this, "it works", Toast.LENGTH_SHORT).show();

            }
        });
        btn_mood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mood = "Decent";

            }
        });
        btn_mood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood = "Bad";

            }
        });

        btn_sleepRating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepRating = "Good";
            }
        });

        btn_sleepRating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepRating = "Decent";

            }
        });
        btn_sleepRating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepRating = "Bad";

            }
        });

        btn_stressLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stressLevel = "High";
                // Toast.makeText(MorningQuizActivity.this, "it works", Toast.LENGTH_SHORT).show();

            }
        });
        btn_stressLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stressLevel = "Moderate";

            }
        });
        btn_stressLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stressLevel = "Low";

            }
        });

    }
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