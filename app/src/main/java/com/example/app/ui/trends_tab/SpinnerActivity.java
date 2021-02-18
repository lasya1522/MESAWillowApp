package com.example.app.ui.trends_tab;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

       /* if (parent.getItemAtPosition(p).equals("past 7 days")){
            if (dailyQuizData.size() > 7) {
                for (int i = 6; i >= 0; i--) {
                    moodList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getMood());
                }
            } else {
                for (int i = dailyQuizData.size(); i >= 0; i--){
                    moodList.add(dailyQuizData.get(dailyQuizData.size()-1-i).getMood());
                }
            }
        } else if (parent.getItemAtPosition(p).equals("past 30 days")){
            if (dailyQuizData.size() > 30) {
                for (int i = 29; i >= 0; i--) {
                    moodList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getMood());
                }
            } else {
                for (int i = dailyQuizData.size(); i >= 0; i--){
                    moodList.add(dailyQuizData.get(dailyQuizData.size()-1-i).getMood());
                }
            }
        } else if (parent.getItemAtPosition(p).equals("all time")){
            for (int i = dailyQuizData.size(); i >= 0; i--){
                moodList.add(dailyQuizData.get(dailyQuizData.size()-1-i).getMood());
            }
        }

        */
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
